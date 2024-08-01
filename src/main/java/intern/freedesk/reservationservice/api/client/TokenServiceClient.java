package intern.freedesk.reservationservice.api.client;

import intern.freedesk.reservationservice.api.request.BaseRequest;
import intern.freedesk.reservationservice.api.response.UserIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "TokenService" , url = "${client.feign.token-service.path}")
public interface TokenServiceClient {

    @PostMapping("${client.feign.token-service.extractEmail}")
    String extractEmail(@RequestBody BaseRequest baseRequest);

    @PostMapping("${client.feign.token-service.extractUserId}")
    UserIdResponse extractUserId(@RequestBody BaseRequest baseRequest);

}
