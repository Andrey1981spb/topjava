package ru.javawebinar.topjava;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void testStyle() throws Exception {
        mockMvc.perform(get("resources/css/style.css"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));

    }
}