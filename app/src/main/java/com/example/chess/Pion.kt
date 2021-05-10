package com.example.chess

import android.graphics.BitmapFactory

class Pion(position: Int, color: Int) : Piece(position, color) {

    override val bitmap = if (color == 0) BitmapFactory.decodeResource(
        res,
        R.drawable.tile005
    ) else BitmapFactory.decodeResource(res, R.drawable.tile012)
    override var type = "pion"

    override fun setLegalPositions(
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ) {
        legalPositions.clear()
        val multiplicateur: Int
        multiplicateur = if (color == 0) 1
        else -1
        val x = position % 8
        val y = position / 8
        if (tourLastMove == 0 && positions[position + (multiplicateur * 16)] == null && positions[position + (multiplicateur * 8)] == null) {
            legalPositions.add(position + (multiplicateur * 16))
        } else if (tourLastMove != 0 && y != 0 && y != 7) {
            if (positions[position + (multiplicateur * 8)] == null) legalPositions.add(position + (multiplicateur * 8))
        }
        if (y != 0 && y != 7) {
            if (positions[position + (multiplicateur * 8)] == null) legalPositions.add(position + (multiplicateur * 8))
        }

        val diaggauche = (position + (multiplicateur * 9) + 2 * color)
        val diagdroit = (position + (multiplicateur * 7) - 2 * color)
        if (y != 0 && y != 7) {
            if (x != 7 && positions[diaggauche] != null && positions[diaggauche]?.getcolor() != color) legalPositions.add(
                diaggauche
            )
            if (x != 0 && positions[diagdroit] != null && positions[diagdroit]?.getcolor() != color) legalPositions.add(
                diagdroit
            )
        }
        //en passant droit blanc
        if (y == 4 && positions[position + 1] != null && color == 0 && positions[position + 1]?.type == "pion" && positions[position + 1]?.gettourLastMove() == (tourMove)) {
            legalPositions.add(position + 9)
        }
        //en passant gauche blanc
        if (y == 4 && positions[position - 1] != null && color == 0 && positions[position - 1]?.type == "pion" && positions[position - 1]?.gettourLastMove() == (tourMove)) {
            legalPositions.add(position + 7)
        }
        //en passant droit noir
        if (y == 3 && positions[position + 1] != null && color == 1 && positions[position + 1]?.type == "pion" && positions[position + 1]?.gettourLastMove() == (tourMove)) {
            legalPositions.add(position - 7)
        }
        if (y == 3 && positions[position - 1] != null && color == 1 && positions[position - 1]?.type == "pion" && positions[position - 1]?.gettourLastMove() == (tourMove)) {
            legalPositions.add(position - 9)
        }
    }

    override fun move(
        to: Int,
        positions: Array<Piece?>,
        tourMove: Int,
        reinesblanches: MutableList<Piece>,
        reinesnoires: MutableList<Piece>
    ) {
        val y = position / 8
        //prise normale
        if (positions[to] != null) {
            positions[to]?.setpris(true)
        }
        //prise en passant blanc droit
        else if (y == 4 && positions[position + 1] != null && color == 0 && positions[position + 1]?.type == "pion" && positions[position + 1]?.gettourLastMove() == (tourMove) && to == position + 9) {
            positions[position + 1]?.setpris(true)
        }
        //prise en passant blanc gauche
        else if (y == 4 && positions[position - 1] != null && color == 0 && positions[position - 1]?.type == "pion" && positions[position - 1]?.gettourLastMove() == (tourMove) && to == position + 7) {
            positions[position - 1]?.setpris(true)
        } else if (y == 3 && positions[position + 1] != null && color == 1 && positions[position + 1]?.type == "pion" && positions[position + 1]?.gettourLastMove() == (tourMove) && to == position - 7) {
            positions[position + 1]?.setpris(true)
        }
        //prise en passant blanc gauche
        else if (y == 3 && positions[position - 1] != null && color == 1 && positions[position - 1]?.type == "pion" && positions[position - 1]?.gettourLastMove() == (tourMove) && to == position - 9) {
            positions[position - 1]?.setpris(true)
        }
        //promotion
        if (to / 8 == 7) {
            position = to
            reinesblanches.add(Reine(position, 0))
            setpris(true)
        }
        if (to / 8 == 0) {
            position = to
            reinesnoires.add(Reine(position, 1))
            setpris(true)
        }
        position = to


    }


}