package com.biscoito.serviceone;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExemploController {

    @RequestMapping("/exemplo-1")
    @PreAuthorize("hasRole('ROLE_MASTER')")
    public String exmplo1() {
        return "ROLE_MASTER";
    }

    @RequestMapping("/exemplo-2")
    @PreAuthorize("hasAnyRole('ROLE_MASTER.CADASTRO_FORNECEDOR', 'ROLE_MASTER.GERAR_REMESSA')")
    public String exmplo2() {
        return "hasAnyRole";
    }

    @RequestMapping("/exemplo-3")
    @PreAuthorize("hasRole('ROLE_MASTER.CADASTRO_FORNECEDOR')")
    public String exmplo3() {
        return "ROLE_MASTER.CADASTRO_FORNECEDOR";
    }

    @RequestMapping("/exemplo-4")
    @PreAuthorize("permitAll()")
    public String exmplo4() {
        return "permitAll";
    }
}
