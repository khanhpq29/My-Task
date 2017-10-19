package ht.pq.khanh.multitask.paint

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R
import java.io.FileOutputStream
import ht.pq.khanh.multitask.MainActivity
import android.R.string.ok
import android.support.design.widget.Snackbar



class PaintFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {
    @BindView(R.id.paint)
    lateinit var paint: PaintView
    private val REQUEST_STORAGE = 0
    private val PERMISSIONS_CONTACT = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.activity_paint)
        ButterKnife.bind(this, view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        paint.init(metrics)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_paint, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal ->
                paint.normal()
            R.id.emboss ->
                paint.emboss()
            R.id.blur ->
                paint.blur()
            R.id.clear ->
                paint.clear()
            R.id.pick_color ->
                paint.setColorPaint(Color.RED)
            R.id.save_paint ->
                save()
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(paint, "permission has been granted",
                        Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(paint, "ok",
                        Snackbar.LENGTH_SHORT).show()

            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        val ref = TaskApplication().getRefWatcher(activity)
        ref.watch(ref)
    }

    private fun save() {
        checkIsGrantPermission()
        saveFile()
    }

    private fun saveFile() {
//        val fileOutput = FileOutputStream("")
//        fileOutput.flush()
//        fileOutput.close()
    }

    private fun checkIsGrantPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            showPermissionDialog()
        }else {
            saveFile()
        }
    }

    private fun showPermissionDialog() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Snackbar.make(paint, "Permission denied",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", {
                        ActivityCompat.requestPermissions(activity,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                REQUEST_STORAGE)
                    })
                    .show()
        }else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_STORAGE)
        }
    }
}