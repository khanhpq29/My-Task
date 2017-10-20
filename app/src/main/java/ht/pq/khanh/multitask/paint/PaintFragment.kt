package ht.pq.khanh.multitask.paint

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.*
import android.view.LayoutInflater
import butterknife.BindView
import butterknife.ButterKnife
import com.android.colorpicker.ColorPickerPalette
import com.android.colorpicker.ColorPickerSwatch
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.dialog.ColorPickerDialog
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R


class PaintFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback, ColorPickerSwatch.OnColorSelectedListener {
    override fun onColorSelected(color: Int) {
        paint.setColorPaint(color)
    }

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
                setColor()
            R.id.save_paint ->
                save()
        }
        return true
    }

    private fun setColor() {
//        val colorPickerDialog = ColorPickerDialog()
        val colors = intArrayOf(R.color.red,
                R.color.pink,
                R.color.purple,
                R.color.deep_purple,
                R.color.indigo,
                R.color.blue,
                R.color.light_blue,
                R.color.cyan,
                R.color.teal,
                R.color.green,
                R.color.lime,
                R.color.yellow,
                R.color.orange,
                R.color.deep_orange,
                R.color.brown,
                R.color.grey,
                R.color.blue_grey)

        val layoutInflater = LayoutInflater.from(context)
        val colorPickerPalette = layoutInflater.inflate(R.layout.color_picker_dialog, null) as ColorPickerPalette
        colorPickerPalette.init(colors.size, 4, this)
        colorPickerPalette.drawPalette(colors,
                R.color.green)
        val alert = AlertDialog.Builder(context)
                .setTitle(R.string.color_picker)
                .setPositiveButton(android.R.string.ok, { dialog, which -> })
                .setNegativeButton(android.R.string.no, { dialog, which -> })
                .setView(colorPickerPalette)
                .create()
        alert.show()
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
        } else {
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
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            showPermissionDialog()
        } else {
            saveFile()
        }
    }

    private fun showPermissionDialog() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(paint, "Permission denied",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", {
                        ActivityCompat.requestPermissions(activity,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                REQUEST_STORAGE)
                    })
                    .show()
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_STORAGE)
        }
    }
}