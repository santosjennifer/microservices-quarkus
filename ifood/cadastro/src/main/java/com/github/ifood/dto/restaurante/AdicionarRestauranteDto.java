package com.github.ifood.dto.restaurante;

import com.github.ifood.dto.LocalizacaoDto;
import com.github.ifood.model.Restaurante;
import com.github.ifood.validation.Dto;
import com.github.ifood.validation.ValidDto;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidDto
public class AdicionarRestauranteDto implements Dto {

    @Size(min = 3, max = 150)
    public String nome;
    
    @NotBlank
    @Pattern(regexp = "(^\\d{2}\\x2E\\d{3}\\x2E\\d{3}\\x2F\\d{4}\\x2D\\d{2}$)")
    public String cnpj;
    
    public LocalizacaoDto localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if(Restaurante.find("cnpj", cnpj).count() > 0){
            context.buildConstraintViolationWithTemplate("Já existe registro para esse CNPJ.")
                    .addPropertyNode("cpnj")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
    
}
