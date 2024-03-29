package com.embeddedproject.myapplication


import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode



class MainActivity : AppCompatActivity() {

    lateinit var arFragment: ArFragment

    //TODO ricordarsi di cambiarlo se cambia il nome del pacchetto
    val PACKAGE_NAME = "com.example.esp22"

    private var model: Renderable? = null
    var cubeRenderable: ModelRenderable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)


        arFragment.setOnTapArPlaneListener { hitResult,plane, motionEvent ->

            /*
            setModel()

            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            val tn = TransformableNode(arFragment.transformationSystem)
            tn.parent = anchorNode
            tn.renderable = cubeRenderable
            tn.select()

            */
            arFragment.arSceneView.scene.addChild(AnchorNode(hitResult.createAnchor()).apply {

                setModel()

                // Create the transformable model and add it to the anchor.
                addChild(TransformableNode(arFragment.transformationSystem).apply {

                    renderable = cubeRenderable

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

    private fun setModel() {

        ModelRenderable.builder()
            .setSource(this, Uri.parse("models/spada.glb") )
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { model: ModelRenderable -> cubeRenderable = model }
            .exceptionally {
                val t = Toast.makeText(this, "Unable to load Cube model", Toast.LENGTH_SHORT)
                t.show()
                null
            }

    }

}



