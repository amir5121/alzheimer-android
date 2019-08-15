package com.amir.alzheimer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.amir.alzheimer.R
import com.amir.alzheimer.activities.games.*
import com.amir.alzheimer.androidpuzzlegame.MainActivityPuzzle
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.infrastructure.adapter.DuplicateAdapter
import com.amir.alzheimer.infrastructure.adapter.IndexedItemAdapter
import kotlinx.android.synthetic.main.game_fragment.view.*

class GameFragment : BaseFragment(), AdapterView.OnItemClickListener {
    private var pickedDomain: Int? = null
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var adapterList: ArrayList<String>
    private lateinit var domainList: LinkedHashMap<String, ArrayList<String>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.game_fragment, container, false)
        context?.let {
            domainList = linkedMapOf(
                    it.getString(R.string.shape_categorizing) to
                            arrayListOf(it.getString(R.string.puzzle),
                                    it.getString(R.string.missing_number),
                                    it.getString(R.string.match),
                                    it.getString(R.string.count),
                                    it.getString(R.string.word_map),
                                    it.getString(R.string.duplicate),
                                    it.getString(R.string.image_map),
                                    it.getString(R.string.duplicate_text)
                            ),
                    it.getString(R.string.important_events) to
                            arrayListOf(
                                    it.getString(R.string.word_map),
                                    it.getString(R.string.party),
                                    it.getString(R.string.celebs),
                                    it.getString(R.string.utilization),
                                    it.getString(R.string.find),
                                    it.getString(R.string.memory),
                                    it.getString(R.string.invert),
                                    it.getString(R.string.flower)
                            ),
                    it.getString(R.string.numerical_memory) to
                            arrayListOf(
                                    it.getString(R.string.number_find),
                                    it.getString(R.string.number_map),
                                    it.getString(R.string.count),
                                    it.getString(R.string.reverse_count),
                                    it.getString(R.string.double_count),
                                    it.getString(R.string.double_count_down),
                                    it.getString(R.string.evaluate)
                            ),
                    it.getString(R.string.procedural_memory) to
                            arrayListOf(),
                    it.getString(R.string.imagination_power) to
                            arrayListOf(),
                    it.getString(R.string.memory_testing_1) to
                            arrayListOf(),
                    it.getString(R.string.memory_testing_2) to
                            arrayListOf()

            )
            adapterList = domainList.keys.toList() as ArrayList<String>
            adapter = ArrayAdapter(
                    it,
                    R.layout.red_list_item,
                    adapterList
            )

            view.game_fragment_list_view.adapter = adapter
            view.game_fragment_list_view.onItemClickListener = this


        }

        return view
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        val activity = activity
        if (activity != null) {
            if (pickedDomain == null) {
                updateListView(domainList.toList()[i].second)
                pickedDomain = i
            } else {
                when (pickedDomain) {
                    0 ->
                        when (i) {

                            0 -> activity.startActivity(Intent(context, MainActivityPuzzle::class.java))
                            1 -> activity.startActivity(Intent(context, NumberGameActivity::class.java))
                            2 -> activity.startActivity(Intent(context, SquareMatchActivity::class.java))
                            3 -> activity.startActivity(Intent(context, OneToFifty::class.java))
                            4 -> activity.startActivity(Intent(context, WordMapActivity::class.java))
                            5 -> activity.startActivity(Intent(context, DuplicateActivity::class.java))
                            6 -> activity.startActivity(Intent(context, IndexedItemActivity::class.java))
                            7 -> {
                                val intent = Intent(context, DuplicateActivity::class.java)
                                intent.putExtra(DuplicateActivity.DUPLICATE_MODE, DuplicateAdapter.TEXT)
                                activity.startActivity(intent)
                            }
                        }
                    1 -> {
                        when (i) {

                            0 -> {
                                val intent = Intent(context, IndexedItemActivity::class.java)
                                intent.putExtra(IndexedItemActivity.MAP_MODE, IndexedItemAdapter.TEXT)
                                activity.startActivity(intent)
                            }

                            1 -> {
                                val intent = Intent(context, QuestionActivity::class.java)
                                intent.putExtra(QuestionActivity.QUESTION, getString(R.string.party_question))
                                activity.startActivity(intent)
                            }

                            2 -> {
                                val intent = Intent(context, QuestionActivity::class.java)
                                intent.putExtra(QuestionActivity.QUESTION, getString(R.string.celebrity_question))
                                intent.putExtra(QuestionActivity.HAS_ANSWER, false)
                                activity.startActivity(intent)
                            }

                            3 -> {
                                val intent = Intent(context, QuestionActivity::class.java)
                                intent.putExtra(QuestionActivity.QUESTION, getString(R.string.utilization_question))
                                intent.putExtra(QuestionActivity.HAS_ANSWER, false)
                                activity.startActivity(intent)
                            }

                            4 -> activity.startActivity(Intent(context, FindActivity::class.java))

                            5 -> {
                                val intent = Intent(context, QuestionActivity::class.java)
                                intent.putExtra(QuestionActivity.QUESTION, getString(R.string.memory_question))
                                intent.putExtra(QuestionActivity.HAS_ANSWER, false)
                                activity.startActivity(intent)
                            }

                            6 -> {
                                val intent = Intent(context, QuestionActivity::class.java)
                                intent.putExtra(QuestionActivity.QUESTION, getString(R.string.invert_question))
                                intent.putExtra(QuestionActivity.HAS_ANSWER, false)
                                activity.startActivity(intent)
                            }

                            7 -> {
                                val intent = Intent(context, QuestionActivity::class.java)
                                intent.putExtra(QuestionActivity.QUESTION, getString(R.string.flower_question))
                                intent.putExtra(QuestionActivity.HAS_ANSWER, false)
                                activity.startActivity(intent)
                            }
                        }
                    }

                    2 -> {
                        when (i) {
                            0 -> {
                                val intent = Intent(context, DuplicateActivity::class.java)
                                intent.putExtra(DuplicateActivity.DUPLICATE_MODE, DuplicateAdapter.NUMBER)
                                activity.startActivity(intent)
                            }

                            1 -> {
                                val intent = Intent(context, IndexedItemActivity::class.java)
                                intent.putExtra(IndexedItemActivity.MAP_MODE, IndexedItemAdapter.NUMBER)
                                activity.startActivity(intent)
                            }

                            2 -> {
                                val intent = Intent(context, OneToFifty::class.java)
                                intent.putExtra(OneToFifty.HARDNESS_INTENT, 2)
                                activity.startActivity(intent)
                            }

                            3 -> {
                                val intent = Intent(context, OneToFifty::class.java)
                                intent.putExtra(OneToFifty.HARDNESS_INTENT, 2)
                                intent.putExtra(OneToFifty.MODE, OneToFifty.COUNT_DOWN)
                                activity.startActivity(intent)
                            }

                            4 -> {
                                val intent = Intent(context, OneToFifty::class.java)
                                intent.putExtra(OneToFifty.HARDNESS_INTENT, 2)
                                intent.putExtra(OneToFifty.MODE, OneToFifty.COUNT_2_UP)
                                activity.startActivity(intent)
                            }

                            5 -> {
                                val intent = Intent(context, OneToFifty::class.java)
                                intent.putExtra(OneToFifty.MODE, OneToFifty.COUNT_2_DOWN)
                                activity.startActivity(intent)
                            }
                            6 -> activity.startActivity(Intent(context, MathQuestionActivity::class.java))
                        }
                    }
                }
            }
        }
    }

    private fun updateListView(newData: ArrayList<String>) {
        adapterList = newData
        adapter.clear()
        adapter.addAll(adapterList)
        adapter.notifyDataSetChanged()
    }

    fun backPressed(): Boolean {
        return if (pickedDomain == null) {
            true
        } else {
            pickedDomain = null
            updateListView(domainList.keys.toList() as ArrayList<String>)
            false
        }
    }
}
