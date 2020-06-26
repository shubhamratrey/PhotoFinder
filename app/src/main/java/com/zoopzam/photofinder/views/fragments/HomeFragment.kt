package com.zoopzam.photofinder.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoopzam.photofinder.R
import com.zoopzam.photofinder.constants.BundleConstants.FOLDER_PATH
import com.zoopzam.photofinder.constants.Constants
import com.zoopzam.photofinder.models.HomeItem
import com.zoopzam.photofinder.models.responses.HomeDataResponse
import com.zoopzam.photofinder.views.adapter.HomeAdapter
import com.zoopzam.photofinder.views.module.HomeFragmentModule
import com.zoopzam.photofinder.views.viewmodal.HomeFragmentViewModel
import com.zoopzam.photofinder.views.viewmodelfactory.FragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), HomeFragmentModule.APIModuleListener {


    override fun onHomeApiSuccess(response: HomeDataResponse?) {
        if (response != null) {
            setHomeAdapter(response)
        }
    }

    override fun onApiFailure(statusCode: Int, message: String) {

    }

    companion object {
        fun newInstance() = HomeFragment()

        fun newInstance(folder_path:String?): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            if (folder_path != null) {
                bundle.putString(FOLDER_PATH, folder_path)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private var viewModel: HomeFragmentViewModel? = null
    private val TAG = HomeFragment::class.java.simpleName
    private var mFolderPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mFolderPath = it.getString(FOLDER_PATH)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_home, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, FragmentViewModelFactory(this@HomeFragment))
                .get(HomeFragmentViewModel::class.java)
        if (mFolderPath!=null) {
            viewModel?.getHomeData(1, mFolderPath!!)
        } else {
            viewModel?.getHomeData(1, null)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.onDestroy()
    }

    private fun setHomeAdapter(response: HomeDataResponse?) {
        if (rcvAll?.adapter == null) {
            val adapter = HomeAdapter(context!!, response!!) { it, pos, type ->
                if (it is HomeItem && it.itemType == Constants.HOME_ITEM_TYPE.FOLDER) {
                    addFragment(newInstance(it.folderPath), "HomeToFolder")
                }
            }
            rcvAll?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rcvAll?.setItemViewCacheSize(10)
            rcvAll?.adapter = adapter
            rcvAll?.visibility = View.VISIBLE
        } else {
            val adapter = rcvAll?.adapter as HomeAdapter
            adapter.addMoreData(response)
        }
    }
}
