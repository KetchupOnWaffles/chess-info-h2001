package com.example.chess

import android.graphics.*


abstract class Piece(protected var position: Int, protected val color: Int) {

    open var type = ""
    protected var pris = false
    protected var tourLastMove = 0
    protected var legalPositions: MutableList<Int> = ArrayList()
    private var position_initiale = position
    var res = MainActivity.getInstance().applicationContext.resources
    abstract val bitmap: Bitmap

    fun getposition(): Int {
        return position
    }

    fun setposition(res: Int) {
        position = res
    }

    fun getcolor(): Int {
        return color
    }

    fun getpris(): Boolean {
        return pris
    }

    fun setpris(res: Boolean) {
        pris = res
    }

    fun gettourLastMove(): Int {
        return tourLastMove
    }

    fun settourLastMove(res: Int) {
        tourLastMove = res
    }

    fun getlegalPositions(): MutableList<Int> {
        return legalPositions
    }

    fun draw(canvas: Canvas?, cote: Int) {
        if (!pris) {
            val x = (position % 8) * cote
            val y = cote * 8 - cote * (position / 8) + cote * 2
            canvas?.drawBitmap(bitmap, null, Rect(x, y, x + cote, y + cote), null)
        }
    }

    open fun isLegal(
        to: Int,
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ): Boolean {
        setLegalPositions(positions, tourMove, menacedPositions)
        return to in legalPositions
    }

    open fun setLegalPositions(
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ) {
    }

    open fun move(
        to: Int,
        positions: Array<Piece?>,
        tourMove: Int,
        reinesblaches: MutableList<Piece>,
        reinesnoires: MutableList<Piece>
    ) {
        if (positions[to] != null) {
            positions[to]?.setpris(true)
        }
        position = to
    }

    open fun resetPiece() {
        pris = false
        tourLastMove = 0
        legalPositions = ArrayList()
        position = position_initiale
    }
}