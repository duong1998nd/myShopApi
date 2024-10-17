package com.example.shop.securityConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils {
    private String SECRET_KEY = "secret";


    // lấy tên người dùng từ mã thông báo jwt
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

 // lấy ngày hết hạn từ mã thông báo jwt
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
  //để lấy bất kỳ thông tin nào từ mã thông báo, chúng tôi sẽ cần khóa bí mật
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

 // kiểm tra xem token đã hết hạn chưa
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

  //tạo mã thông báo cho người dùng
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

 // trong khi tạo mã thông báo -
  //1. Xác định xác nhận quyền sở hữu của mã thông báo, như Tổ chức phát hành, Ngày hết hạn, Chủ đề và ID
  //2. Ký JWT bằng thuật toán HS512 và khóa bí mật.
  //3. Theo JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  // nén JWT thành chuỗi an toàn cho URL
    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
        		.claim("authorities", userDetails.getAuthorities())
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}