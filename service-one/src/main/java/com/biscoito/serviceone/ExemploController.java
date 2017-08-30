package com.biscoito.serviceone;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExemploController {

    @RequestMapping("/exemplo-1")
    @PreAuthorize("hasRole('ROLE_TESTE')")
    public String exmplo1() {
        return "ROLE_TESTE";
    }

    @RequestMapping("/exemplo-2")
    @PreAuthorize("hasAnyRole('ROLE_TESTE', 'ROLE_MASTER', 'ROLE_OUTRA')")
    public String exmplo2() {
        return "hasAnyRole";
    }

    @RequestMapping("/exemplo-3")
    @PreAuthorize("hasRole('ROLE_PERFIL.FUNCIONALIDADE')")
    public String exmplo3() {
        return "FUNCIONALIDADE";
    }

    @RequestMapping("/exemplo-4")
    @PreAuthorize("permitAll()")
    public String exmplo4() {
        return "permitAll";
    }
}
