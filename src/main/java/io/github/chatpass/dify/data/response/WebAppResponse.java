package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebAppResponse {

    private String title;

    private String chatColorTheme;

    private boolean chatColorThemeInverted;

    private String iconType;

    private String icon;

    private String iconBackground;

    private String iconUrl;

    private String description;

    private String copyright;

    private String privacyPolicy;

    private String customDisclaimer;

    private String defaultLanguage;

    private boolean showWorkflowSteps;

    private boolean useIconAsAnswerIcon;
}
