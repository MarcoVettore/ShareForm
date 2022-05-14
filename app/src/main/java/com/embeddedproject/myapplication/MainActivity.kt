package com.embeddedproject.myapplication

import android.content.UriMatcher
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    lateinit var arFragment :ArFragment
    //TODO ricordarsi di cambiarlo se cambia il nome del pacchetto
    val PACKAGE_NAME = "com.embeddedproject.myapplication"

    var cubeRenderable : ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createModel(1)
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult,plane, motionEvent ->
            arFragment.arSceneView.scene.addChild(AnchorNode(hitResult.createAnchor()).apply {
                // Create the transformable model and add it to the anchor.
                addChild(TransformableNode(arFragment.transformationSystem).apply {
                    val model = createModel(1)

                    renderable = cubeRenderable
                    //renderableInstance.animate(true).start()
                    // Add child model relative the a parent model
                    addChild(Node().apply {
                        // Define the relative position
                        localPosition = Vector3(0.0f, 1f, 0.0f)
                        // Define the relative scale
                        localScale = Vector3(0.7f, 0.7f, 0.7f)
                        //renderable = modelView
                    })
                })
            })
        }
    }
    fun createModel( index : Int){



         ModelRenderable.builder()
            .setSource(this, Uri.parse("android.resource://"+PACKAGE_NAME+"/" + R.raw.cubo))
            .setIsFilamentGltf(true)
            .build()
            .thenAccept{model : ModelRenderable -> cubeRenderable = model}





    }

}