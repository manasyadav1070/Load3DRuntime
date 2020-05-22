package com.example.load3druntime;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {
private ArFragment arfragment;
private String asset = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arfragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arfragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            placemodel(hitResult.createAnchor());
        });

    }

    private void placemodel(Anchor anchor) {

        ModelRenderable
                .builder()
                .setSource(
                        this,
                        RenderableSource
                        .builder()
                        .setSource(this, Uri.parse(asset), RenderableSource.SourceType.GLTF2)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build()
                )
                .setRegistryId(asset)
                .build()
                .thenAccept(modelRenderable -> addtoscene(modelRenderable , anchor));
    }

    private void addtoscene(ModelRenderable modelRenderable, Anchor anchor) {

        AnchorNode anchornode = new AnchorNode(anchor);
        anchornode.setRenderable(modelRenderable);
        arfragment.getArSceneView().getScene().addChild(anchornode);

    }
}
