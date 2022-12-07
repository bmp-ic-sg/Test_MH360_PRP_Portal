package sg.ic.pg.manager;

import javax.inject.Singleton;
import com.parkway.sdh.gw.encryption.controller.APIUtil;
import com.parkway.sdh.gw.encryption.model.Payload;
import sg.ic.pg.util.property.Property;

@Singleton
public class APIEncryptionManager {

    private static APIEncryptionManager instance;

    private APIUtil apiUtil;

    private APIEncryptionManager() {
        String decryptPrivateKey = EncryptionManager.getInstance()
                .decrypt(PropertyManager.getInstance().getProperty(Property.APP_SERVER_KEY));
        String publicKey = PropertyManager.getInstance().getProperty(Property.API_SERVER_PUBLIC_KEY);
        apiUtil = new APIUtil(publicKey, decryptPrivateKey);
    }


    public Payload encrypt(String message) {
        return apiUtil.encrypt(message);
    }

    public String decrypt(Payload payload) {
        return apiUtil.decrypt(payload);
    }

    public static APIEncryptionManager getInstance() {

        if (instance == null) {
            instance = new APIEncryptionManager();
        }

        return instance;
    }

}
