package com.example.chess

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.util.AttributeSet
import android.util.SparseIntArray
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.scheduleAtFixedRate
import java.util.Timer as Timer1

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

class DrawingView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attributes, defStyleAttr) {
    private val textPaint = Paint()
    private var drawing = true

    //creation des cases
    private val cases = Array(64) { index ->
        Cases(index)
    }
    private val pionsblancs = Array(8) { index ->
        Pion(index + 8, 0)
    }
    private val pionsnoirs = Array(8) { index ->
        Pion(index + (6 * 8), 1)
    }
    private val toursblancs = Array(2) { index ->
        Tour((index * 7), 0)
    }
    private val toursnoirs = Array(2) { index ->
        Tour((index * 7) + 56, 1)
    }
    private val cavaliersblancs = Array(2) { index ->
        Cavalier(1 + (index * 5), 0)
    }
    private val cavaliersnoirs = Array(2) { index ->
        Cavalier((1 + (index * 5)) + 56, 1)
    }
    private val fousblancs = Array(2) { index ->
        Fou(2 + (index * 3), 0)
    }
    private val fousnoirs = Array(2) { index ->
        Fou((2 + (index * 3)) + 56, 1)
    }
    private var reinesblanches: MutableList<Piece> = ArrayList()
    private var reinesnoires: MutableList<Piece> = ArrayList()
    private val roiblanc = Roi(4, 0)
    private val roinoir = Roi(60, 1)

    private var customDialog = CustomDialogClass(context)
    private var chrono = 0
    private var totalsec = 0
    private var totaltime = 0
    private val soundPool: SoundPool
    private val soundMap: SparseIntArray
    private var mat = 0

    private var positions = arrayOfNulls<Piece>(64)
    private var pieces_prises: MutableList<Piece> = ArrayList()
    private var checkpos = arrayOfNulls<Piece>(64)
    private var menacedPositions: MutableList<Int> = ArrayList()
    private var possibleMoves: MutableList<Int> = ArrayList()

    private var select1 = 64
    private var cliccount = 0
    private var select2 = 64
    private var tour = -1

    init {
        chronometre()
        val audioAttributes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }

        soundPool = SoundPool.Builder().setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        soundMap = SparseIntArray(3)
        soundMap.put(0, soundPool.load(context, R.raw.move, 1))
        soundMap.put(1, soundPool.load(context, R.raw.gagne, 1))
        soundMap.put(2, soundPool.load(context, R.raw.perdu, 1))
        customDialog.showDialog()
    }


    fun setpos() {
        positions = arrayOfNulls(64)

        if (tour == -1) {
            reinesblanches.add(Reine(3, 0))
            reinesnoires.add(Reine(59, 1))
            tour += 1

        }
        for (pion in pionsblancs) {
            if (!pion.getpris()) positions[pion.getposition()] = pion
            else if (pion !in pieces_prises) pieces_prises.add(pion)
        }
        for (pion in pionsnoirs) {
            if (!pion.getpris()) positions[pion.getposition()] = pion
            else if (pion !in pieces_prises) pieces_prises.add(pion)
        }
        for (tour in toursblancs) {
            if (!tour.getpris()) positions[tour.getposition()] = tour
            else if (tour !in pieces_prises) pieces_prises.add(tour)
        }
        for (tour in toursnoirs) {
            if (!tour.getpris()) positions[tour.getposition()] = tour
            else if (tour !in pieces_prises) pieces_prises.add(tour)
        }
        for (cav in cavaliersblancs) {
            if (!cav.getpris()) positions[cav.getposition()] = cav
            else if (cav !in pieces_prises) pieces_prises.add(cav)
        }
        for (cav in cavaliersnoirs) {
            if (!cav.getpris()) positions[cav.getposition()] = cav
            else if (cav !in pieces_prises) pieces_prises.add(cav)
        }
        for (fou in fousnoirs) {
            if (!fou.getpris()) positions[fou.getposition()] = fou
            else if (fou !in pieces_prises) pieces_prises.add(fou)
        }
        for (fou in fousblancs) {
            if (!fou.getpris()) positions[fou.getposition()] = fou
            else if (fou !in pieces_prises) pieces_prises.add(fou)
        }
        for (reine in reinesblanches) {
            if (!reine.getpris()) positions[reine.getposition()] = reine
            else if (reine !in pieces_prises) pieces_prises.add(reine)
        }
        for (reine in reinesnoires) {
            if (!reine.getpris()) positions[reine.getposition()] = reine
            else if (reine !in pieces_prises) pieces_prises.add(reine)
        }
        if (!roiblanc.getpris()) positions[roiblanc.getposition()] = roiblanc
        if (!roinoir.getpris()) positions[roinoir.getposition()] = roinoir

        textPaint.textSize = width / 20f
        textPaint.color = Color.WHITE
    }


    fun check() {
        checkpos = arrayOfNulls<Piece>(64)
        for (pion in pionsblancs) {
            if (!pion.getpris()) checkpos[pion.getposition()] = pion
        }
        for (pion in pionsnoirs) {
            if (!pion.getpris()) checkpos[pion.getposition()] = pion
        }
        for (tour in toursblancs) {
            if (!tour.getpris()) checkpos[tour.getposition()] = tour
        }
        for (tour in toursnoirs) {
            if (!tour.getpris()) checkpos[tour.getposition()] = tour
        }
        for (cav in cavaliersblancs) {
            if (!cav.getpris()) checkpos[cav.getposition()] = cav
        }
        for (cav in cavaliersnoirs) {
            if (!cav.getpris()) checkpos[cav.getposition()] = cav
        }
        for (fou in fousnoirs) {
            if (!fou.getpris()) checkpos[fou.getposition()] = fou
        }
        for (fou in fousblancs) {
            if (!fou.getpris()) checkpos[fou.getposition()] = fou
        }
        for (reine in reinesblanches) {
            if (!reine.getpris()) checkpos[reine.getposition()] = reine
        }
        for (reine in reinesnoires) {
            if (!reine.getpris()) checkpos[reine.getposition()] = reine
        }
        if (!roiblanc.getpris()) checkpos[roiblanc.getposition()] = roiblanc
        if (!roinoir.getpris()) checkpos[roinoir.getposition()] = roinoir
    }


    fun menace(pos: Array<Piece?>, color: Int) {
        menacedPositions.clear()
        //les positions menacees par les pieces de la couleur opposee a celle donnee
        for (piece in pos) {
            if (piece != null) {
                if (piece.getcolor() != color) {
                    piece.setLegalPositions(pos, tour, menacedPositions)
                    menacedPositions =
                        (menacedPositions + piece.getlegalPositions()).toMutableList()
                }
            }
        }
    }


    fun simulAllPos(pos: Array<Piece?>, color: Int) {
        possibleMoves.clear()
        var index = 0
        for (piece in pos) {
            if (piece != null && piece.getcolor() == color) {
                for (i in piece.getlegalPositions()) {
                    positions[index]?.move(i, positions, tour, reinesblanches, reinesnoires)
                    check()
                    menace(checkpos, tour % 2)
                    if (tour % 2 == 0) {
                        if (!(roiblanc.getposition() in menacedPositions)) {
                            possibleMoves.add(i)
                        }
                    } else if (tour % 2 == 1) {
                        if (!(roinoir.getposition() in menacedPositions)) {
                            possibleMoves.add(i)
                        }
                    }
                    piece.setposition(index)
                    if ((piece.type == "pion" && index / 8 == 6 && piece.getcolor() == 0) || (piece.type == "pion" && index / 8 == 1 && piece.getcolor() == 1)) {
                        piece.setpris(false)
                        if (color == 0) {
                            reinesblanches.removeAt(reinesblanches.size - 1)
                        }
                        if (color == 1) {
                            reinesnoires.removeAt(reinesnoires.size - 1)
                        }
                    }
                    positions[i]?.setpris(false)
                    if (piece.type == "pion" && index < 63) positions[index - 1]?.setpris(false)
                    if (piece.type == "pion" && index > 0) positions[index + 1]?.setpris(false)
                }
            }
            index++
        }

    }

    fun echec() {
        if (roiblanc.getposition() in menacedPositions) {
            cases[roiblanc.getposition()].setcolor(Color.argb(255, 255, 100, 100))
        }
        if (roinoir.getposition() in menacedPositions) {
            cases[roinoir.getposition()].setcolor(Color.argb(255, 255, 100, 100))
        }
    }


    fun echecetmat() {
        if (possibleMoves.size == 0 && tour != 0) {
            if (roiblanc.getposition() in menacedPositions || roinoir.getposition() in menacedPositions) {
                mat = 1
            } else mat = 2
        }
    }

    //clique
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val x = e.x
        val y = e.y
        val cote = width / 8
        val colonne = ((x / cote) % 8).toInt()
        val ligne = 7 - ((y / cote - 3).toInt())
        val id = colonne + 8 * ligne

        if (y < cote * 12 && y > cote * 3 && e.action == 1) {
            if (cliccount == 0 && positions[id] != null) {
                if (positions[id]?.getcolor() == tour % 2) {
                    select1 = id
                    positions[select1]?.setLegalPositions(positions, tour, menacedPositions)
                    echec()
                    if (positions[select1]?.getlegalPositions() != null) {
                        for (i in positions[select1]?.getlegalPositions()!!) {
                            cases[i].setcolor(Color.argb(255, 118, 150, 200))
                            invalidate()
                        }
                    }
                    cliccount++
                }

            } else if (cliccount == 1) {
                select2 = id
            }

            if (select1 != 64 && select2 != 64) {
                if (positions[select1]?.isLegal(
                        select2,
                        positions,
                        tour,
                        menacedPositions
                    ) == true
                ) {
                    val piece = positions[select1]
                    positions[select1]?.move(select2, positions, tour, reinesblanches, reinesnoires)
                    chrono = customDialog.getChrono()
                    totalsec = chrono * 60
                    check()

                    if (tour % 2 == 0) {
                        menace(checkpos, tour % 2)
                        if (roiblanc.getposition() in menacedPositions) {
                            positions[select1]?.setposition(select1)
                            positions[select2]?.setpris(false)
                            if (positions[select1]?.type == "pion" && select1 < 63) positions[select1 - 1]?.setpris(
                                false
                            )
                            if (positions[select1]?.type == "pion" && select1 > 0) positions[select1 + 1]?.setpris(
                                false
                            )
                            if (piece?.type == "pion" && select1 / 8 == 6) {
                                piece.setpris(false)
                                if (tour % 2 == 0) {
                                    reinesblanches.removeAt(reinesblanches.size - 1)
                                }
                            }
                        } else {
                            tour++
                            positions[select1]?.settourLastMove(tour)
                            playPieceSound()
                        }

                    } else if (tour % 2 == 1) {

                        menace(checkpos, tour % 2)
                        if (roinoir.getposition() in menacedPositions) {
                            positions[select1]?.setposition(select1)
                            positions[select2]?.setpris(false)
                            positions[select1 + 1]?.setpris(false)
                            positions[select1 - 1]?.setpris(false)
                            if (piece?.type == "pion" && select1 / 8 == 1) {
                                piece.setpris(false)
                                if (tour % 2 == 1) {
                                    reinesnoires.removeAt(reinesnoires.size - 1)
                                }
                            }
                        } else {
                            tour++
                            positions[select1]?.settourLastMove(tour)
                            playPieceSound()
                        }
                    }
                }

                for (case in cases) {
                    case.clear()
                }
                invalidate()
                select1 = 64
                select2 = 64
                cliccount = 0
            }
        }
        setpos()
        menace(positions, tour % 2)
        simulAllPos(positions, tour % 2)
        echecetmat()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        if (tour == -1) setpos()
        super.onDraw(canvas)
        if (drawing) {
            val minutes = (totalsec / 60)
            val secondes = (totalsec - minutes * 60)
            val formatted = String.format("%02d:%02d", minutes.toLong(), secondes.toLong())
            canvas?.drawText(
                formatted,
                30f, 50f, textPaint
            )
        }
        setBackgroundResource(R.drawable.fond4)
        for (case in cases) {
            case.draw(canvas, width / 8)
        }
        for (piece in positions) {
            piece?.draw(canvas, width / 8)
        }
    }


    fun chronometre() {
        val timer = Timer1("schedule", true)
        timer.scheduleAtFixedRate(1000, 1000) {
            totalsec -= 1
            endGame()
            invalidate()
            drawing = true
            if (totalsec <= 0) {
                drawing = false
            }
            totaltime += 1
        }
    }


    fun playPieceSound() {
        soundPool.play(soundMap.get(0), 1f, 1f, 1, 0, 1f)
    }

    fun playGagneSound() {
        soundPool.play(soundMap.get(1), 1f, 1f, 1, 0, 1f)
    }

    fun playPerduSound() {
        soundPool.play(soundMap.get(2), 1f, 1f, 1, 0, 1f)
    }


    fun endGame() {
        if (tour != 0) {
            if (tour % 2 == 1 && mat == 1) {
                customDialog.showEndGameDialog(resources.getString(R.string.gagne), totaltime)
                playGagneSound()
                newGame()
            } else if (tour % 2 == 0 && mat == 1) {
                customDialog.showEndGameDialog(resources.getString(R.string.perdu), totaltime)
                playPerduSound()
                newGame()
            } else if (mat == 2) {
                customDialog.showEndGameDialog(resources.getString(R.string.egalite), totaltime)
                newGame()
            } else if (totalsec == 0) {
                customDialog.showEndGameDialog(resources.getString(R.string.ecoule), totaltime)
                playPerduSound()
                newGame()
            }
        }
    }


    fun newGame() {
        drawing = true
        tour = 0
        customDialog.setChrono(0)
        totalsec = 0
        totaltime = 0
        mat = 0
        for (i in positions) {
            i?.resetPiece()
        }
        for (j in pieces_prises) {
            j.resetPiece()
        }
        setpos()
        invalidate()
    }

    fun pause() {
        drawing = false
    }

    fun resume() {
        drawing = true
    }
}