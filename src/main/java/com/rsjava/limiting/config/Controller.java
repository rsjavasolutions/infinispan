
package com.rsjava.limiting.config;

import com.rsjava.limiting.service.LimitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final LimitService limitService;

    public Controller(LimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping("ip/{ip}")
    public void checkIp(@PathVariable String ip) {
        limitService.checkIpLimit(ip);
    }

    @GetMapping("name/{name}")
    public void checkName(@PathVariable String name) {
        limitService.checkNameLimit(name);
    }

    @GetMapping("surname/{surname}")
    public void checkSurname(@PathVariable String surname) {
        limitService.checkSurnameLimit(surname);
    }

    @GetMapping("regon/{regon}")
    public void checkRegon(@PathVariable String regon) {
        limitService.checkRegonLimit(regon);
    }
}
