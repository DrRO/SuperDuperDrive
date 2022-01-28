package com.udacity.jwdnd.course1.cloudstorage.TabServices;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Models.CredentialModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServices {

    private UserMapper userMapper;
    private CredentialMapper credentialMapper;

    public CredentialServices(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }
// insert new credential
    public void newCredential(String url, String userName, String credentialUserName, String key, String password) {
        CredentialModel credential = new CredentialModel(
                0,
                url,
                credentialUserName,
                key,
                password,
                userMapper.getUser(userName).getUserId());
        credentialMapper.insertCred(credential);



    }
// Get Credential info from credentialMapper
    public List<CredentialModel> getCredentialList(Integer userId) {
        return credentialMapper.getCredentialList(userId);
    }

    public CredentialModel getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    // update existing credential method
    public void updateCredential(Integer credentialId, String newUserName, String url, String key, String password) {
        credentialMapper.updateCredential(credentialId, newUserName, url, key, password);
    }
    // delete existing credential
    public void deleteCredential(Integer noteId) {
        credentialMapper.deleteCredential(noteId);
    }


}
