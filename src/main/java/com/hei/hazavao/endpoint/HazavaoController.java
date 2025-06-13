package com.hei.hazavao.endpoint;

import com.hei.hazavao.service.HazavaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hazavao")
public class HazavaoController {

  private final HazavaoService hazavaoService;

  public HazavaoController(HazavaoService hazavaoService) {
    this.hazavaoService = hazavaoService;
  }

  @GetMapping
  public String getDefinition(@RequestParam String teny) {
    return hazavaoService.getDefinition(teny);
  }
}
