package intern.freedesk.reservationservice.api.client;

import intern.freedesk.reservationservice.api.response.UserIdResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "UserService" , url = "${client.feign.user-service.path}")
public interface UserServiceClient {

    @GetMapping("${client.feign.user-service.extractUserIdByEmail}")
    UserIdResponse extractUserIDByEmail (@NotBlank @RequestParam("email") String email);

    @GetMapping("${client.feign.user-service.extractEmailByUserId}")
    UserIdResponse extractRoleFromToken (@NotBlank @PathVariable("userId") String userId);


}
