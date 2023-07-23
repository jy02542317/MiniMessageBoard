package learning.java.minimessageboard.AOP;

import jakarta.servlet.http.HttpServletRequest;
import learning.java.minimessageboard.Entities.TbRoomEntity;
import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Services.JwtServices;
import learning.java.minimessageboard.Services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Aspect
@Component
@Slf4j
public class AspectSecurity {

    @Autowired
    JwtServices jwtServices;

    @Autowired
    UserServices userServices;


    // 定义一个切入点，参数是定义在哪个包。哪个类、哪个方法切入，关于切入点如何定义？
    @Pointcut("execution(public * learning.java.minimessageboard.Controllers.MessageController.*(..))")
    public void pointFn(){}

    // 定义一个通知,在执行pointFn这个方法之前（切入进去之前），我们需要执行check方法
    @Before("pointFn()")
    public void check() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest httpServletRequest = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String username=jwtServices.extractUsername(token);
        TbUserEntity tbUserEntity=userServices.getTbUserEntityByUserName(username).get();
        String sRoomId=(httpServletRequest.getParameter("roomId"));

        if(!sRoomId.equals(null)){
           int roomId= Integer.parseInt(sRoomId);

           List<TbRoomEntity> list=tbUserEntity.getRoomList();
           boolean result=false;
           for(TbRoomEntity tbRoomEntity:list){
                if(tbRoomEntity.getId().equals(roomId)){
                    result=true;
                }
           }
           if(!result){
               log.info("该用户对这个room没有权限");
               return;
           }
        }




        log.info("对MessageController做check检查");
    }
}
