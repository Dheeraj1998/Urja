package io.github.dheeraj1998.oorja;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class ChatActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //region Variable initialisation
    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter mAdapter;
    private TextView message_entered;
    private TextToSpeech tts;
    private String query_message;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tts = new TextToSpeech(this, this);
        tts.setSpeechRate(Float.parseFloat("0.8"));

        message_entered = (TextView) findViewById(R.id.edittext_chatbox);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        mAdapter = new MessageAdapter(messageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void sendMessage(View view) {
        if (checkConnection(getCurrentFocus())) {
            final AIConfiguration config = new AIConfiguration("47837cfe3577406494120309a647ebd6", AIConfiguration.SupportedLanguages.English);
            final AIRequest aiRequest = new AIRequest();

            query_message = message_entered.getText().toString();

            if (query_message.length() == 0) {
                Toast.makeText(getApplicationContext(), "Enter something!", Toast.LENGTH_SHORT).show();
            } else {
                Message temp = new Message();
                temp.setMessage_content(query_message);
                temp.setMessage_type(1);
                messageList.add(temp);

                mAdapter.notifyDataSetChanged();
                aiRequest.setQuery(query_message);
            }

            //region Code-block for handling the task of setting up the API.AI query
            final AsyncTask<AIRequest, Integer, AIResponse> task =
                    new AsyncTask<AIRequest, Integer, AIResponse>() {
                        private AIError aiError;

                        @Override
                        protected AIResponse doInBackground(final AIRequest... params) {
                            final AIRequest request = params[0];
                            try {
                                final AIDataService aiDataService = new AIDataService(config);
                                final AIResponse response = aiDataService.request(request);
                                // Return response
                                return response;
                            } catch (final AIServiceException e) {
                                aiError = new AIError(e);
                                return null;
                            }
                        }

                        @Override
                        protected void onPostExecute(final AIResponse response) {
                            if (response != null) {
                                onResult(response);
                            } else {
                                onError(aiError);
                            }
                        }
                    };
            task.execute(aiRequest);
            //endregion
        }
    }

    private void onResult(AIResponse response) {
        final Result result = response.getResult();
        String agent_reply = result.getFulfillment().getSpeech();

        Message temp = new Message();
        temp.setMessage_content(agent_reply);
        temp.setMessage_type(2);
        messageList.add(temp);

        tts.speak(agent_reply, TextToSpeech.QUEUE_FLUSH, null);
        message_entered.setText("");

        mAdapter.notifyDataSetChanged();
    }

    private void onError(AIError error) {

    }

    //region Function for checking the Internet connectivity
    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }
    //endregion

    //region Function for handling no Internet connectivity
    public boolean checkConnection(View view) {
        RotateLoading rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.error_view);

        try {
            if (!isConnected()) {
                relativeLayout.setVisibility(View.VISIBLE);
                rotateLoading.start();

                return false;
            } else {
                rotateLoading.stop();

                relativeLayout.setVisibility(View.GONE);

                return true;
            }
        } catch (InterruptedException e) {
            relativeLayout.setVisibility(View.VISIBLE);
            rotateLoading.start();

            return false;
        } catch (IOException e) {
            relativeLayout.setVisibility(View.VISIBLE);
            rotateLoading.start();

            return false;
        }
    }
    //endregion

    //region onInit function
    @Override
    public void onInit(int status) {

    }
    //endregion
}
