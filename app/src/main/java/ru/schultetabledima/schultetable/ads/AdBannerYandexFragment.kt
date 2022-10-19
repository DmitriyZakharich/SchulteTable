package ru.schultetabledima.schultetable.ads

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import ru.schultetabledima.schultetable.R


class AdBannerYandexFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        Log.d("fffffffffffff", "onCreateView")

        return inflater.inflate(R.layout.fragment_ad_banner_yandex, container, false)
    }

    override fun onResume() {
        Log.d("fffffffffffff", "onResume:")

        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mBannerAdView = view.findViewById<BannerAdView>(R.id.banner_view)
        mBannerAdView.setAdUnitId("R-M-DEMO-300x250")   //тестовый id

        Log.d("fffffffffffff", "onViewCreated")

        val adRequest = AdRequest.Builder().build()
        mBannerAdView.setBannerAdEventListener(object : BannerAdEventListener{
            override fun onAdLoaded() {}
            override fun onAdFailedToLoad(p0: AdRequestError) {}
            override fun onAdClicked() {}
            override fun onLeftApplication() {}
            override fun onReturnedToApplication() {}
            override fun onImpression(p0: ImpressionData?) {}
        })
        mBannerAdView.loadAd(adRequest)
        mBannerAdView.setAdSize(AdSize.stickySize(AdSize.FULL_SCREEN.width))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fffffffffffff", "onDestroy:")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fffffffffffff", "onDetach:")

    }

    companion object {fun newInstance() = AdBannerYandexFragment() }
}