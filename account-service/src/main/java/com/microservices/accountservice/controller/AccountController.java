package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.AccountDto;
import com.microservices.accountservice.entity.Account;
import com.microservices.accountservice.repository.AccountRepository;
import com.microservices.accountservice.s3.S3Utility;
import com.microservices.accountservice.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/account")
public class AccountController {

    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private Environment environment;

    private List<String> contentList;

    @Autowired
    public AccountService accountService;

    @Autowired
    public AccountRepository accountRepository;

    private S3Utility s3Utility;

    AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.s3Utility = new S3Utility(accountService, accountRepository);
    }

    @GetMapping("test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String test() {
        return "Token Access test";
    }

    @GetMapping("")
    public ResponseEntity< List<AccountDto>> getAllAccounts() {

        List<AccountDto> accountDtos = accountService.getAllAccounts();
        if(accountDtos!=null) {
            LOGGER.info("All account list  {}", accountDtos.get(1).getId());
            return new ResponseEntity<>(accountDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }

    @GetMapping("all")
    public ResponseEntity< List<Account>> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping("delete/all")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAllAccount() {
        accountService.deleteAllAccount();
        return "Delete all accounts";
    }

    @GetMapping("download")
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> download() {

        // bucket: keith-salesforce-bucket-hk
        // key: test.txt
        // aws_access_key_id = AKIA3N6JDPP2WJLAF3YX
        // aws_secret_access_key = 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
        // (environment variable)AWS_ACCESS_KEY_ID / AWS_SECRET_ACCESS_KEY

        /*
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                  "your_access_key_id",
                  "your_secret_access_key");

                S3Client s3 = S3Client.builder()
                            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                            .build();
         */

        AwsBasicCredentials awsCreds = s3Utility.getAwsCredentials();

        String bucketName = "sfaccounttest"; // salesforce-ceterna / sfaccounttest

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        //Region region = Region.AP_EAST_1;
        Region region = Region.AP_NORTHEAST_1;

        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                //.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        s3Utility.listBucketObjects(s3, bucketName);

        s3.close();

        return contentList;

    }

    // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/java_s3_code_examples.html
    @GetMapping("/account/upload")
    public String upload() {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIAUQ6735MDOW6NSB",
                "H+bw7K6f8Wr574+bHp6I+VN6zDEUpaaLV+J");

        String bucketName = "salesforce-hk-to-jp"; // salesforce-hk-to-jp / salesforce-ceterna / sfaccounttest

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        //Region region = Region.AP_EAST_1;
        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        String result = s3Utility.putS3Object(s3, bucketName, "keith.txt");

        s3.close();

        return result;
    }

    @GetMapping("/account/move")
    public String moveToProcessed() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIAUQ63MDOW6NSBt67",
                "H+bw7K6f8Wrn346bHp6I+VN6zDEUpaaLV+J");

        String bucketName = "salesforce-hk-to-jp"; // salesforce-hk-to-jp / salesforce-ceterna / sfaccounttest

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        //Region region = Region.AP_EAST_1;
        Region region = Region.AP_NORTHEAST_1;
        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        String result = s3Utility.copyBucketObject(s3, "salesforce-hk-to-jp", "keith.txt", "salesforce-processed-files");

        s3.close();

        return result;
    }

}
