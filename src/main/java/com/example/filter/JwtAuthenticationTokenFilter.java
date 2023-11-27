package com.example.filter;

/**
 * @author orall
 */
/*
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token（token放在头部）
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        try{
            //获取请求时的token
            JwtUtils jwt = JwtUtils.getInstance();
            Claims claims = jwt.check(token);
            if (claims != null) {
                String username = (String) claims.get("username");
                filterChain.doFilter(request,response);
            }else{
                throw new RuntimeException("用户未登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

    }
}
*/