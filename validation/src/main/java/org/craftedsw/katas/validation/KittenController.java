package org.craftedsw.katas.validation;

import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 *
 */
@RestController
@Validated
public class KittenController {

    public static class KittenDTO implements Serializable {

        private  String violations;
        private  Kitten kitten;

        public KittenDTO(){}

        public void setViolations(String violations) {
            this.violations = violations;
        }

        public void setKitten(Kitten kitten) {
            this.kitten = kitten;
        }

        public KittenDTO(String violations, Kitten kitten) {
            this.violations = violations;
            this.kitten = kitten;
        }

        public String getViolations() {
            return violations;
        }

        public Kitten getKitten() {
            return kitten;
        }
    }

    /**
     * http://localhost:8080/register?name=kitty&age=10&breed=cutest
     * http://localhost:8080/register?name=&age=10&breed=cutest
     *
     * @param name
     * @param age
     * @param breed
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void registerKittenWithAttributes(@RequestParam(name = "name") @NotBlank String name,
                                             @RequestParam(name = "age") @Range(min = 0, max = 30) int age,
                                             @RequestParam(name = "breed") @Pattern(regexp = "\\w+") String breed,
                                             @RequestParam(name = "birhtDate", required = false) @Past LocalDate birthDate) {
        Kitten kitten = new Kitten(name, age, breed, birthDate);
        System.out.println("Kitten registered " + kitten);
    }


    @RequestMapping(value = "/postKitten", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<KittenDTO> postRegisterKittenWithBean(@Valid @NotNull @RequestBody Kitten kitten) {
        System.out.println("Kitten registered " + kitten);
        return new ResponseEntity<KittenDTO>(new KittenDTO(null, kitten), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<KittenDTO> handleViolation(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = fieldError.getDefaultMessage();
            System.out.println("message =>" + localizedErrorMessage);
        }
        return new ResponseEntity<KittenDTO>(new KittenDTO(fieldErrors.toString(), null), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/miaou", method = RequestMethod.GET)
    public String miaou() {
        return "miaou!";
    }


}
