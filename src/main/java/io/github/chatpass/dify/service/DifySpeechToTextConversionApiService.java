package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.TextToAudioRequest;
import io.github.chatpass.dify.data.response.AudioToTextResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DifySpeechToTextConversionApiService {
    /**
     * 文字转语音
     * @param request
     * @return
     */
    @POST("text-to-audio")
    Call<ResponseBody> textToAudio(@Body TextToAudioRequest request);

    /**
     * 语音转文字
     * @param file
     * @param user
     * @return
     */
    @Multipart
    @POST("audio-to-text")
    Call<AudioToTextResponse> audioToText(
            @Part MultipartBody.Part file,
            @Part MultipartBody.Part user
    );
}
