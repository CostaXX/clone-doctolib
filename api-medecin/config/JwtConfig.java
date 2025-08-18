import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.OctetSequenceKey;

@Configuration
public class JwtConfig {

    private static final String SECRET_KEY = "ma_cle_super_secure_256bits"; // 32+ caractères

    @Bean
    public JwtEncoder jwtEncoder() {
        OctetSequenceKey octetKey = new OctetSequenceKey.Builder(SECRET_KEY.getBytes()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableSecret<>(octetKey);
        return new NimbusJwtEncoder(jwkSource);
    }
}