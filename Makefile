
keystore:
	@echo "Creating keystore..."
	@keytool -genkeypair -alias coucal -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore coucal.p12 -validity 3650 -dname "CN=Ben Fortuna, OU=, O=Node Logic, L=, ST=, C=au"
	@echo "Keystore created successfully."