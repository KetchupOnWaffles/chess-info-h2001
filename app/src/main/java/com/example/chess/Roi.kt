package com.example.chess

import android.graphics.BitmapFactory

class Roi(position: Int, color: Int) : Piece(position, color) {
    override val bitmap = if (color == 0) BitmapFactory.decodeResource(
        res,
        R.drawable.tile000
    ) else BitmapFactory.decodeResource(res, R.drawable.tile007)

    override fun isLegal(
        to: Int,
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ): Boolean {
        setLegalPositions(positions, tourMove, menacedPositions)
        if (position + 2 in menacedPositions || position + 1 in menacedPositions || position in menacedPositions) {
            legalPositions.remove(position + 2)
        }
        if (position - 2 in menacedPositions || position - 1 in menacedPositions || position - 3 in menacedPositions || position in menacedPositions) {
            legalPositions.remove(position + 2)
        }
        return to in legalPositions
    }

    override fun setLegalPositions(
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ) {
        legalPositions.clear()

        if (tourLastMove == 0) {
            if (positions[position + 1] == null && positions[position + 2] == null) {
                if (positions[position + 3]?.type == "tour" && positions[position + 3]?.gettourLastMove() == 0) {
                    legalPositions.add(position + 2)
                }
            }
            if (positions[position - 1] == null && positions[position - 2] == null && positions[position - 3] == null) {
                if (positions[position - 4]?.type == "tour" && positions[position - 4]?.gettourLastMove() == 0) {
                    legalPositions.add(position - 2)
                }
            }
        }


        val x = position % 8
        val y = position / 8
        var nx: Int
        var ny: Int
        nx = x + 1
        ny = y + 1
        //droite
        if (!(nx > 7 || nx < 0)) if (positions[8 * y + nx] == null || positions[8 * y + nx]?.getcolor() != color) legalPositions.add(
            8 * y + nx
        )
        //haut
        if (!(ny > 7 || ny < 0)) if (positions[8 * ny + x] == null || positions[8 * ny + x]?.getcolor() != color) legalPositions.add(
            8 * ny + x
        )
        //haut droit
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        ny = y - 1
        //bas droit
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        nx = x - 1
        //gauche
        if (!(nx > 7 || nx < 0)) if (positions[8 * y + nx] == null || positions[8 * y + nx]?.getcolor() != color) legalPositions.add(
            8 * y + nx
        )
        //bas
        if (!(ny > 7 || ny < 0)) if (positions[8 * ny + x] == null || positions[8 * ny + x]?.getcolor() != color) legalPositions.add(
            8 * ny + x
        )
        //bas gauche
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //haut gauche
        ny = y + 1
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
    }

    override fun move(
        to: Int,
        positions: Array<Piece?>,
        tourMove: Int,
        reinesblaches: MutableList<Piece>,
        reinesnoires: MutableList<Piece>
    ) {
        if (to == position + 2) {
            positions[position + 3]?.setposition(position + 1)
            position = to
        }
        if (to == position - 2) {
            positions[position - 4]?.setposition(position - 1)
            position = to
        }
        if (positions[to] != null) {
            positions[to]?.setpris(true)
        }
        position = to
    }


}