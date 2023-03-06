package com.microservices.accountservice.s3;

import com.microservices.accountservice.entity.Account;
import com.microservices.accountservice.repository.AccountRepository;
import com.microservices.accountservice.repository.MemberRepository;
import com.microservices.accountservice.service.MemberService;
import com.google.gson.Gson;
import com.microservices.accountservice.dto.AccountDto;
import com.microservices.accountservice.service.AccountService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class S3Utility {

    Logger LOGGER = LoggerFactory.getLogger(S3Utility.class);

    @Autowired
    public AccountService accountService;

    @Autowired
    public MemberService memberService;

    public AccountRepository accountRepository;

    public MemberRepository memberRepository;

    public S3Utility(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public S3Utility(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    public AwsBasicCredentials getAwsCredentials() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIAUQGKYEJXMDOW6NSB",
                "H+bw7K6f8WrnH1urWCO+bHp6I+VN6zDEUpaaLV+J");

        return awsCreds;
    }

    public void listBucketObjects(S3Client s3, String bucketName) {

        try {

            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3.listObjects(listObjects);

            //long unixTime = Instant.now().getEpochSecond();

            List<S3Object> objects = res.contents();
            for (S3Object myValue : objects) {

                LOGGER.info("The name of the key is {}", myValue.key() );
                LOGGER.info("The object is {}", calKb(myValue.size()));
                LOGGER.info("The owner is {}",myValue.owner());

                byte[] data = getObjectBytes(s3, bucketName, myValue.key(),  "/path");

                // Convert Bytes to JSON String
                String fileContent = new String(data, StandardCharsets.UTF_8);

                JSONArray jsonArray = new JSONArray();
                // Read JSON String line by line, and put into JsonArray

                fileContent.
                        lines().
                        forEach(
                                (line) ->
                                {
                                    // convert "Id" field from Salesforce to "sfid" in AWS
                                    String newLine = line.replaceFirst("Id", "sfid");
                                    //logger.info("The Content newLine:  {}", newLine );
                                    JSONObject jsonObject = new JSONObject(newLine);
                                    jsonArray.put(jsonObject);
                                }
                        );

                LOGGER.info("The jsonArray Content:  {}", jsonArray.toString() );

                Gson gson = new Gson();
                ModelMapper modelMapper = new ModelMapper();
                Integer totalInsert =0;
                Integer totalUpdate =0;

                for(Integer i=0; i<jsonArray.length() ; i++) {
                    Account account = gson.fromJson( (jsonArray.getJSONObject(i).toString()), Account.class);

                    //logger.info("The account.getSfid():  {}", account.getSfid() );

                    // check if exist by id
                    List<Account> isFoundAccount = accountService.findLimitedTo(1,account.getSfid() );

                    if(isFoundAccount.size()==0)
                    {
                        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
                        AccountDto savedAccountDto = accountService.saveAccount(accountDto);

                        LOGGER.info("Inert '{}' Account {} | SfId: {}",  myValue.key(), i+1 , account.getSfid());

                        /*
                        logger.info("'{}' Account {} | name: {}", myValue.key(), i+1 , account.getName() );
                        logger.info("'{}' Account {} | recordTypeId: {}", myValue.key(), i+1 , account.getRecordTypeId() );
                        logger.info("'{}' Account Object {} | parentId: {}", myValue.key(), i+1 , account.getParentId() );
                        logger.info("'{}' Account Object {} | ownerId: {}", myValue.key(), i+1 , account.getOwnerId() );
                        logger.info("'{}' Account Object {} | photoUrl: {}", myValue.key(), i+1 , account.getPhotoUrl() );
                        logger.info("'{}' Account Object {} | createdDate: {}", myValue.key(), i+1 , account.getCreatedDate() );
                        logger.info("'{}' Account Object {} | LastModifiedDate: {}", myValue.key(), i+1 , account.getLastModifiedDate() );
                        */
                        totalInsert++;

                    } else {
                        // Update Account Record
                        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
                        //AccountDto savedAccountDto = accountService.updateAccount(accountDto);
                        LOGGER.info("Update Account {}");
                        totalUpdate++;
                    }

                }

                LOGGER.info("totalInsert: {}",  totalInsert);
                LOGGER.info("totalUpdate: {}",  totalUpdate);

            }

        } catch (S3Exception e) {
            LOGGER.info("[exception] {}", e.awsErrorDetails().errorMessage() );
        }
    }

    private byte[] getObjectBytes (S3Client s3, String bucketName, String keyName, String path) {

        try {

            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
            byte[] data = objectBytes.asByteArray();

            return data;

        } catch (S3Exception e) {
            LOGGER.info("[exception] {}", e.awsErrorDetails().errorMessage() );
        }

        return null;
    }

    //convert bytes to kbs.
    private long calKb(Long val) {
        return val/1024;
    }


    // Upload functions

    public String putS3Object(S3Client s3, String bucketName, String objectKey) {

        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

            List<Account> accounts = accountRepository.findAll();

            // convert Object to Byte[] , or Convert Byte[] to Object
            // Account deserializedUser = (Account) SerializationUtils.deserialize(data);
            // byte[] data = SerializationUtils.serialize(accounts);

            Gson gson = new Gson();
            String jsonString = gson.toJson(accounts);
            byte[] data =  jsonString.getBytes();

            PutObjectResponse response = s3.putObject(putOb, software.amazon.awssdk.core.sync.RequestBody.fromBytes(data));

            LOGGER.info("[putS3Object]: {}", response);

            return "file uploaded successfully!";

        } catch (S3Exception e) {
            LOGGER.info("[S3Exception] {}", e.getMessage());
        }

        return "No file uploaded";
    }

    // Return a byte array.
    private byte[] getObjectFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytesArray;
    }

    public static String copyBucketObject (S3Client s3, String fromBucket, String objectKey, String toBucket) {

        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(fromBucket + "/" + objectKey, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("URL could not be encoded: " + e.getMessage());
        }
        CopyObjectRequest copyReq = CopyObjectRequest.builder()
                .copySource(encodedUrl)
                .destinationBucket(toBucket)
                .destinationKey(objectKey)
                .build();

        try {
            CopyObjectResponse copyRes = s3.copyObject(copyReq);
            System.out.println("The "+ objectKey +" was copied to "+toBucket);
            return copyRes.copyObjectResult().toString();

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return "";
    }

    public static void deleteBucketObjects(S3Client s3, String bucketName) {

        // Upload three sample objects to the specfied Amazon S3 bucket.
        ArrayList<ObjectIdentifier> keys = new ArrayList<>();
        PutObjectRequest putOb;
        ObjectIdentifier objectId;

        for (int i = 0; i < 3; i++) {
            String keyName = "delete object example " + i;
            objectId = ObjectIdentifier.builder()
                    .key(keyName)
                    .build();

            putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            s3.putObject(putOb, software.amazon.awssdk.core.sync.RequestBody.fromString(keyName));
            keys.add(objectId);
        }

        System.out.println(keys.size() + " objects successfully created.");

        // Delete multiple objects in one request.
        Delete del = Delete.builder()
                .objects(keys)
                .build();

        try {
            DeleteObjectsRequest multiObjectDeleteRequest = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(del)
                    .build();

            s3.deleteObjects(multiObjectDeleteRequest);
            System.out.println("Multiple objects are deleted!");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
