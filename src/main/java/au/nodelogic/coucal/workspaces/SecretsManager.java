/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package au.nodelogic.coucal.workspaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

/**
 * Provides access to secrets storage for channel configurations, etc.
 */
@Service
public class SecretsManager {

    private final Environment env;

    private final KeyStore keyStore;

    private boolean keyStoreInitialized = false;

    private KeyStore.PasswordProtection passwordProtection;

    public SecretsManager(@Autowired Environment env) throws KeyStoreException {
        this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        this.env = env;
    }

    public void setMasterPassword(char[] masterPassword) {
        this.passwordProtection = new KeyStore.PasswordProtection(masterPassword);
    }

    public boolean hasMasterPassword() {
        return passwordProtection != null;
    }

    public KeyStore.Entry getSecret(String alias) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        return keyStore.getEntry(alias, passwordProtection);
    }

    public void putSecret(String alias, KeyStore.Entry entry) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        if (!keyStoreInitialized) {
            load(env.getProperty("keystore.path"));
        }
        keyStore.setEntry(alias, entry, passwordProtection);
        save(env.getProperty("keystore.path"));
    }

    public void load(String path) throws CertificateException, IOException, NoSuchAlgorithmException {
        try (FileInputStream fis = new FileInputStream(path)) {
            keyStore.load(fis, passwordProtection.getPassword());
            keyStoreInitialized = true;
        }
    }

    private void save(String path) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            keyStore.store(fos, passwordProtection.getPassword());
        }
    }
}
