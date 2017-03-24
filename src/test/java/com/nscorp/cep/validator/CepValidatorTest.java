package com.nscorp.cep.validator;

import com.nscorp.cep.exception.InvalidCepException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by raphael on 23/03/17.
 */
public class CepValidatorTest {

    private CepValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new CepValidator();
    }

    @Test(expected = InvalidCepException.class)
    public void returna_cep_invalido_alfanumerico() throws Exception {
        validator.validate("abc");
    }

    @Test(expected = InvalidCepException.class)
    public void returna_cep_invalido_com_espaco() throws Exception {
        validator.validate("999999 9");
    }

    @Test
    public void returna_cep_valido() throws Exception {
        validator.validate("99999999");
        assertThat(validator.isValid("99999999"), is(true));
    }

    @Test
    public void returna_cep_invalido_com_numero_a_menos() throws Exception {
        assertThat(validator.isValid("9999999"), is(false));
    }

    @Test
    public void returna_cep_invalido_com_numero_a_mais() throws Exception {
        assertThat(validator.isValid("99999999999999"), is(false));
    }
}