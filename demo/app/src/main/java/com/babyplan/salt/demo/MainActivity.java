package com.babyplan.salt.demo;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends BaseActivity {
    static String pic_url;
    private static int Camera_Request_Code=1;
    ImageView imageView,imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        imageView= (ImageView) findViewById(R.id.show);//显示拍摄图片
        imageView1= (ImageView) findViewById(R.id.down);//显示下载图片
        Bmob.initialize(this,"efe95be40a610d6b7a274eebf4f70d91");   //这里改成你的开发者tag

    }

    public void Camera(View v){
        Log.i("-----打开摄像头-----","摄像头启动");
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,Camera_Request_Code);//根据不同请求返回，这里=Camera_Request_Code=1时返回
    }



    public void Download(View v){

        Log.i("-----准备下载-----","点击事件开始");
        ImageRequest imageRequest=new ImageRequest(
//                "http://upload.news.cecb2b.com/2013/1127/1385516912891.png",
                pic_url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView1.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView1.setImageResource(R.mipmap.ic_launcher);//失败用这张图片
                toast("fail");
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);//创建一个volley队列
        mQueue.add(imageRequest);//加入队列 开始下载
        Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Camera_Request_Code){
            Log.i("-----返回成功-----","摄像头启动");
            if (data==null){
                Log.i("-----返回数据空-----","用户没有进行拍照");
                return;
            }else {
                Bundle result=data.getExtras();
                if (result!=null){
                    Bitmap bm=result.getParcelable("data");//拿到数据存入bm
                    Uri uri=BitMap(bm);
                    imageView.setImageBitmap(bm);
                    Toast.makeText(MainActivity.this, "拍摄成功！", Toast.LENGTH_SHORT).show();
                    String picPath  = "sdcard/Bmobupload.png";


                    final BmobFile bmobFile = new BmobFile(new File(picPath));
                    bmobFile.uploadblock(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                toast("上传文件成功:" + bmobFile.getFileUrl() );
                                com.babyplan.salt.demo.bean.Movie p2 = new com.babyplan.salt.demo.bean.Movie();
                                p2.setName("lucky");
                                p2.setFile(bmobFile);
                                pic_url=bmobFile.getFileUrl();
                                p2.seturl(bmobFile.getFileUrl());
                                p2.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId,BmobException e) {
                                        if(e==null){
                                            toast("添加数据成功，返回objectId为："+objectId);
                                        }else{
                                            toast("创建数据失败：" + e.getMessage());
                                        }
                                    }
                                });
                            }else{
                                toast("上传文件失败：" + e.getMessage());
                            }
                        }
                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                        }
                    });
                }
            }
        }
    }

    //存放图片的地址，可以改动
    private Uri BitMap(Bitmap bitmap){
        File tmpDir=new File(Environment.getExternalStorageDirectory()+"/Bmob");    //保存地址及命名
        if (!tmpDir.exists()){
            tmpDir.mkdir(); //生成目录进行保存
        }
        File img=new File(tmpDir.getAbsolutePath()+"upload.png");
        try {
            FileOutputStream fos=new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);  //参:压缩的格式，图片质量85，输出流
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}