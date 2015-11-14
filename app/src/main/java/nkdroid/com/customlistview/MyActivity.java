package nkdroid.com.customlistview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import nkdroid.com.customlistview.image_loader.ImageLoader;


public class MyActivity extends Activity {

    private ProgressDialog progressDialog;
    private ListView postList;
    private ArrayList<BeanPost> beanPostArrayList;
    private PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        postList=(ListView)findViewById(R.id.postList);

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(MyActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                Reader reader=API.getData("http://beta.json-generator.com/api/json/get/KQiRLjN");

                Type listType = new TypeToken<ArrayList<BeanPost>>(){}.getType();
                beanPostArrayList = new GsonBuilder().create().fromJson(reader, listType);
                handleJsonData();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }
        }.execute();
    }

    public void handleJsonData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                postAdapter=new PostAdapter(MyActivity.this,beanPostArrayList);
                postList.setAdapter(postAdapter);
            }
        });
    }

    public class PostAdapter extends BaseAdapter {

        Context context;
        ArrayList<BeanPost> postArrayList;
        ImageLoader imageLoader;

        public PostAdapter(Context context, ArrayList<BeanPost> postArrayList) {
            imageLoader=new ImageLoader(MyActivity.this);
            this.context = context;
            this.postArrayList = postArrayList;
        }

        public int getCount() {
            return postArrayList.size();
        }

        public Object getItem(int position) {
            return postArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView txtPostTitle,txtPostDate,txtPostDescription;
            ImageView imgPost;
        }

        public View getView(final int position, View convertView,ViewGroup parent) {

            final ViewHolder holder;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_list, parent, false);
                holder = new ViewHolder();
                holder.txtPostTitle = (TextView) convertView.findViewById(R.id.txtPostTitle);
                holder.txtPostDate = (TextView) convertView.findViewById(R.id.txtPostDate);
                holder.txtPostDescription = (TextView) convertView.findViewById(R.id.txtPostDescription);
                holder.imgPost = (ImageView) convertView.findViewById(R.id.imgPost);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtPostTitle.setText(postArrayList.get(position).getPost_title());
            holder.txtPostDate.setText(postArrayList.get(position).getPost_date());
            holder.txtPostDescription.setText(postArrayList.get(position).getDescription());
            imageLoader.DisplayImage(postArrayList.get(position).getImage_url(),holder.imgPost);
            return convertView;
        }

    }

}
