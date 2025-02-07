package com.gamaza.examples.digimon.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

/**
 * Problem Detail custom class
 */
public class ProblemDetailDto extends ProblemDetail {

    /**
     * Builder
     */
    public static class ProblemDetailDtoBuilder {

        // Response
        private final ProblemDetailDto problemDetailDto;

        /**
         * Constructor
         */
        private ProblemDetailDtoBuilder(HttpStatus status, String detail) {
            this.problemDetailDto = new ProblemDetailDto();
            this.problemDetailDto.setStatus(status.value());
            this.problemDetailDto.setDetail(detail);
        }

        public static ProblemDetailDtoBuilder newInstance(HttpStatus status, String detail) {
            return new ProblemDetailDtoBuilder(status, detail);
        }

        public ProblemDetailDtoBuilder withType(URI type) {
            this.problemDetailDto.setType(type);
            return this;
        }

        public ProblemDetailDtoBuilder withTitle(String title) {
            this.problemDetailDto.setTitle(title);
            return this;
        }

        public ProblemDetailDtoBuilder withProperty(String key, Object value) {
            this.problemDetailDto.setProperty(key, value);
            return this;
        }

        public ProblemDetailDto build() {
            return this.problemDetailDto;
        }

    }

}
