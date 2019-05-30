package com.amir.alzheimer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.puzzle.ExampleJigsawConfigurations
import com.amir.alzheimer.puzzle.JigsawPuzzle
import com.amir.alzheimer.puzzle.PuzzleCompactSurface

class PuzzleFragment : BaseFragment() {
    private lateinit var puzzleSurface: PuzzleCompactSurface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val config = ExampleJigsawConfigurations.getRcatKittenExample()

        puzzleSurface = PuzzleCompactSurface(context)
        val jigsawPuzzle = JigsawPuzzle(context, config)
        puzzleSurface.setPuzzle(jigsawPuzzle)

        return puzzleSurface
    }

    override fun onPause() {
        super.onPause()
        puzzleSurface.thread.pause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        puzzleSurface.thread.saveState(outState)
    }

    companion object {

        private val TAG = "PuzzleActivity"
    }
}
