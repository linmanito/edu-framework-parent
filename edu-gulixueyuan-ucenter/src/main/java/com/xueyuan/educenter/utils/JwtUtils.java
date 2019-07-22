package com.xueyuan.educenter.utils;


import com.xueyuan.educenter.entity.UcenterMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class JwtUtils {


    public static final String SUBJECT = "guli";

    //秘钥
    public static final String APPSECRET = "guli";//根据秘钥进行加密，通过钥匙进行加锁，可以随便写

    public static final long EXPIRE = 1000 * 60 * 30;  //过期时间，毫秒，30分钟


    /**
     * 生成jwt token
     * 1.根据对象生成jwt字符串
     *
     * @param member
     * @return
     */
    public static String geneJsonWebToken(UcenterMember member) {

        if (member == null || StringUtils.isEmpty(member.getId())
                || StringUtils.isEmpty(member.getNickname())
                || StringUtils.isEmpty(member.getAvatar())) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)

                //第二部分主体信息
                .claim("id", member.getId())
                .claim("nickname", member.getNickname())
                .claim("avatar", member.getAvatar())


                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();//根据上面的秘钥以HS256来生成字符串

        return token;
    }


    /**
     * 校验jwt token
     * 2.根据jwt生成字符串
     *
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
        return claims;
    }

    //测试生成jwt token
    private static String testGeneJwt(){
        UcenterMember member = new UcenterMember();
        member.setId("999");
        member.setAvatar("www.guli.com");
        member.setNickname("Helen");

        String token = JwtUtils.geneJsonWebToken(member);
        System.out.println(token);
        return token;
    }

    //测试校验jwt token
    private static void testCheck(String token){

        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String id = (String)claims.get("id");
        System.out.println(nickname);
        System.out.println(avatar);
        System.out.println(id);
    }


    public static void main(String[] args){
        UcenterMember member = new UcenterMember();
        member.setId("22222");
        member.setNickname("test");
        member.setAvatar("134213213");

        String token = geneJsonWebToken(member);

        System.out.println(token);

        Claims claims = checkJWT(token);

        System.out.println(claims.get("id"));
        System.out.println(claims.get("nickname"));
        System.out.println(claims.get("avatar"));

    }
}
