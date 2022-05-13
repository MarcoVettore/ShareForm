package com.embeddedproject.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    lateinit var arFragment :ArFragment

    var cubeRenderable : ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.scene_form_fragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult,plane, motionEvent ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            createModel(anchorNode,1)
        }
    }
    fun createModel(anchorNode: AnchorNode, model : Int){

        val model = TransformableNode(arFragment.transformationSystem)

        model.setParent(anchorNode)

        ModelRenderable.builder()
            .setSource(this,R.raw.cube)
            .build()
            .thenAccept{model : ModelRenderable -> cubeRenderable = model}
            .exceptionally{}
        

        //model.renderable =
        model.select()

    }

}