package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.demo.App;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/12 下午10:37
 * 描述:
 */
public class FrescoDemoActivity extends AppCompatActivity {
    private BGABanner mContentBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_demo);

        mContentBanner = (BGABanner) findViewById(R.id.banner_fresco_demo_content);
        mContentBanner.setDelegate(new BGABanner.Delegate<SimpleDraweeView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, SimpleDraweeView itemView, String model, int position) {
                Toast.makeText(banner.getContext(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });
        mContentBanner.setAdapter(new BGABanner.Adapter<SimpleDraweeView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, SimpleDraweeView itemView, String model, int position) {
                itemView.setImageURI(Uri.parse(model));
            }
        });

        App.getInstance().getEngine().fetchItemsWithItemCount(5).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mContentBanner.setData(R.layout.item_fresco, bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
