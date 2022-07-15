package com.example.photogalery

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.photogalery.databinding.FragmentPhotoPageBinding

class PhotoPageFragment : PollNotificationHandlerFragment(R.layout.fragment_photo_page) {

    private lateinit var uri: Uri
    private lateinit var binding: FragmentPhotoPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uri = arguments?.getParcelable(ARG_URI) ?: Uri.EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingIntoWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadingIntoWebView() {

        with(binding){
            progressBar.max = 100
            webView.apply {
                settings.javaScriptEnabled = true
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(webView: WebView?, newProgress: Int) {
                        if (newProgress == 100) {
                            binding.progressBar.visibility = View.GONE
                        } else {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.progressBar.progress = newProgress
                        }
                    }

                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        (activity as AppCompatActivity).supportActionBar?.subtitle = title
                    }
                }

                webViewClient = WebViewClient()
                loadUrl(uri.toString())
            }
        }
    }

    companion object {
        private const val ARG_URI = "photo_page_url"

        fun newInstance(uri: Uri): PhotoPageFragment {
            return PhotoPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_URI, uri)
                }
            }
        }
    }
}