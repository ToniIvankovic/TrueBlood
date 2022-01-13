package progi.megatron.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import progi.megatron.model.SecureToken;
import progi.megatron.repository.SecureTokenRepository;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = StandardCharsets.UTF_8;

    private int tokenValidityInHours = 24*365;

    private final SecureTokenRepository secureTokenRepository;

    public SecureTokenService(SecureTokenRepository secureTokenRepository) {
        this.secureTokenRepository = secureTokenRepository;
    }

    public SecureToken createSecureToken(){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusHours(getTokenValidityInHours()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    public void saveSecureToken(SecureToken token) {
        secureTokenRepository.save(token);
    }

    public SecureToken findByToken(String token) {
        return secureTokenRepository.findByToken(token);
    }

    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }

    public int getTokenValidityInHours() {
        return tokenValidityInHours;
    }

}