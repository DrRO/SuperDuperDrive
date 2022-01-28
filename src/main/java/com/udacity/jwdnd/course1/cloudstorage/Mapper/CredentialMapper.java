package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.Models.UserModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    // Query for credential id
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    CredentialModel getCredential(Integer credentialId);

    // Query for credential list
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<CredentialModel> getCredentialList(Integer userId);

    // Insert new credential
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " + "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insertCred(CredentialModel credential);

    // update existing credential
    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = #{password}, username = #{newUserName} WHERE credentialid = #{credentialId}")
    void updateCredential(Integer credentialId, String newUserName, String url, String key, String password);

    // delete existing credential
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);




}

