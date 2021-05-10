package com.example.chess

import android.graphics.BitmapFactory

class Cavalier(position: Int, color: Int) : Piece(position, color) {
    override val bitmap = if (color == 0) BitmapFactory.decodeResource(
        res,
        R.drawable.tile003
    ) else BitmapFactory.decodeResource(res, R.drawable.tile010)

    override fun setLegalPositions(
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ) {
        legalPositions.clear()
        val x = position % 8
        val y = position / 8
        var nx: Int
        var ny: Int
        //1
        nx = x + 2
        ny = y + 1
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //2
        nx = x + 2
        ny = y - 1
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //3
        nx = x + 1
        ny = y + 2
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //4
        nx = x + 1
        ny = y - 2
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //5
        nx = x - 2
        ny = y + 1
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //6
        nx = x - 2
        ny = y - 1
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //7
        nx = x - 1
        ny = y + 2
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
        //8
        nx = x - 1
        ny = y - 2
        if (!(nx > 7 || nx < 0 || ny > 7 || ny < 0)) if (positions[8 * ny + nx] == null || positions[8 * ny + nx]?.getcolor() != color) legalPositions.add(
            8 * ny + nx
        )
    }


}