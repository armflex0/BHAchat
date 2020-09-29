package bhachat.com.Fragments;

import bhachat.com.Notifications.MyResponse;
import bhachat.com.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAeceAy9M:APA91bEzZntC2u--leOAtFprJKUE2ANRX5oVzZm1OeVhxdytWXVUu6kZvSwQm3bHnyEuQTw2vPJH_uV8JHoUFauzWu_z6sJvbox5x2-wEl8zlyJgVFdq4dEnUGUzjIUhm1C61pLvZ-Mw"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
