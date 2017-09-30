package com.aganticsoft.phyxhidephotosandvideos.view.fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aganticsoft.phyxhidephotosandvideos.Constants;
import com.aganticsoft.phyxhidephotosandvideos.PhyxApp;
import com.aganticsoft.phyxhidephotosandvideos.R;
import com.aganticsoft.phyxhidephotosandvideos.di.Injectable;
import com.aganticsoft.phyxhidephotosandvideos.model.MediaModel;
import com.aganticsoft.phyxhidephotosandvideos.util.CryptoUtils;
import com.aganticsoft.phyxhidephotosandvideos.util.FilePathUtils;
import com.aganticsoft.phyxhidephotosandvideos.util.PrefManager;
import com.aganticsoft.phyxhidephotosandvideos.view.activity.MediaChooseActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ttson
 * Date: 9/22/2017.
 */

public class AlbumFragment extends BaseFragment implements Injectable {


    @BindView(R.id.fabAddFile)
    FloatingActionButton fabAddFile;
    @BindView(R.id.fabAddPhoto)
    FloatingActionButton fabAddPhoto;
    @BindView(R.id.fabAddVideo)
    FloatingActionButton fabAddVideo;
    @BindView(R.id.fabAddAlbum)
    FloatingActionButton fabAddAlbum;
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu fabMenus;

    public static final int REQUEST_CHOOSE_IMAGES = 0x91;
    public static final int REQUEST_CHOOSE_VIDEOS = 0x92;
    public static final int REQUEST_CHOOSE_FILES = 0x93;


    @Inject
    PrefManager prefManager;
    private Context mContext;

    public static AlbumFragment newInstance() {
        AlbumFragment frg = new AlbumFragment();
        Bundle bundle = new Bundle();

        frg.setArguments(bundle);

        return frg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this , v);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_FILES:
                    ClipData clipData = data.getClipData();
                    Uri uri = data.getData();

                    if (uri != null) { // single file pick
                        saveToStorage(uri);
                    }
                    else if (clipData != null && clipData.getItemCount() > 0 ) { // multiple file click
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            String path = item.getUri().toString();

                            saveToStorage(item.getUri());
                        }
                    }

                    break;
                case REQUEST_CHOOSE_IMAGES:
                    break;
                case REQUEST_CHOOSE_VIDEOS:
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    private void saveToStorage(Uri uri) {
        if (uri.toString().startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri
                        , new String[] {
                                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
                        }, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    InputStream in =  getActivity().getContentResolver().openInputStream(uri);
                    int numBytes = in.available();
                    byte[] buffer = new byte[numBytes];
                    in.read(buffer);

                    CryptoUtils.saveFile(PhyxApp.getInstance().getStorageManager(), buffer, Constants.PATH_MAINALBUM
                             + displayName);

                    // TODO: fix cannot delete
                    String realPath = FilePathUtils.getPath(mContext, uri);
                    File originalFile = new File(realPath);
                    originalFile.delete();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if (uri.toString().startsWith("file://")) {
            File originalFile = new File(uri.getPath());

            int size = (int) originalFile.length();
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(originalFile));
                buf.read(bytes, 0, bytes.length);
                buf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            CryptoUtils.saveFile(PhyxApp.getInstance().getStorageManager()
                    , bytes, Constants.PATH_MAINALBUM + originalFile.getName());

            originalFile.delete();
        }


    }

    // <editor-fold desc="[ =============== BIND CLICK ===================]">

    @OnClick(R.id.fabAddPhoto)
    public void onClickAddPhoto() {
        startActivityForResult(MediaChooseActivity.getIntent(mContext
                , MediaModel.MediaType.TYPE_IMAGE), REQUEST_CHOOSE_IMAGES);

        fabMenus.collapse();
    }

    @OnClick(R.id.fabAddVideo)
    public void onClickAddVideo() {
        startActivityForResult(MediaChooseActivity.getIntent(mContext
                , MediaModel.MediaType.TYPE_VIDEO), REQUEST_CHOOSE_VIDEOS);
        fabMenus.collapse();
    }

    @OnClick(R.id.fabAddAlbum)
    public void onClickAddAlbum() {
        fabMenus.collapse();
    }

    @OnClick(R.id.fabAddFile)
    public void onClickAddFile() {
        fabMenus.collapse();

        Intent intent = new Intent();
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Files"), REQUEST_CHOOSE_FILES);

        Toast.makeText(mContext, "Long hold item to multi select !", Toast.LENGTH_SHORT).show();
    }

    // </editor-fold>
}
