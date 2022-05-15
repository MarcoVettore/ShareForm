package com.embeddedproject.myapplication


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.gorisse.thomas.sceneform.util.getResourceUri
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var arFragment: ArFragment

    //TODO ricordarsi di cambiarlo se cambia il nome del pacchetto
    val PACKAGE_NAME = "com.example.esp22"

    private var model: Renderable? = null
    var cubeRenderable: ModelRenderable? = null
    //var res = getResources()

    /*private val onTapPlane = arFragment.setOnTapArPlaneListener { hitResult,plane, motionEvent ->
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
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)


        /*arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->

            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            createModel(anchorNode, 1)*/

        arFragment.setOnTapArPlaneListener { hitResult,plane, motionEvent ->

            arFragment.arSceneView.scene.addChild(AnchorNode(hitResult.createAnchor()).apply {

                setModel()

                // Create the transformable model and add it to the anchor.
                addChild(TransformableNode(arFragment.transformationSystem).apply {
                    //val model = createModel(1)

                    renderable = cubeRenderable
                    //renderableInstance.setCulling(false)
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

    private fun setModel() {


        ModelRenderable.builder()
            .setSource(this, Uri.parse("models/cubo3.glb") )
            .setIsFilamentGltf(true)
            .build()
            .thenAccept { model: ModelRenderable -> cubeRenderable = model }
            .exceptionally {
                val t = Toast.makeText(this, "Unable to load Cube model", Toast.LENGTH_SHORT)
                t.show()
                null
            }

    }

    /*
    interface MyCallable<V> : Callable<V> {
        fun setStdIn(`in`: InputStream?)
        fun setStdOut(out: InputStream?)
    }

    class CallableTask : MyCallable<InputStream?> {
        private var input :InputStream? = null
        private var out: InputStream? = null

        override fun setStdIn(inp: InputStream?) {
            input = inp
        }

        override fun setStdOut(out: InputStream?) {
            this.out = out
        }

        @Throws(Exception::class)

        override fun call(): InputStream? {

            return this.out
        }




    }
*/

}



