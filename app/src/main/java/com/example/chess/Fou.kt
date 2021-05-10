package com.example.chess

import android.graphics.BitmapFactory

class Fou(position: Int, color: Int) : Piece(position, color) {

    override val bitmap = if (color == 0) BitmapFactory.decodeResource(
        res,
        R.drawable.tile002
    ) else BitmapFactory.decodeResource(res, R.drawable.tile009)

    override fun setLegalPositions(
        positions: Array<Piece?>,
        tourMove: Int,
        menacedPositions: MutableList<Int>
    ) {
        legalPositions.clear()
        val x = position % 8
        val y = position / 8

        if (x != 0 && y != 7) {
            for (i in 1..7) {
                val pos = position + 7 * i
                val posx = pos % 8
                if (pos < 64) {
                    if (positions[pos] == null) {
                        legalPositions.add(pos)
                    }

                    if (positions[pos] != null) {
                        if (positions[pos]?.getcolor() == this.color) {
                            break
                        } else {
                            legalPositions.add(pos)
                            break
                        }
                    }
                    if (posx == 7 || posx == 0) {
                        break
                    }
                }
            }
        }
        if (x != 0 && y != 0) {
            for (i in 1..7) {
                val pos = position - 9 * i
                val posx = pos % 8
                if (pos > 0) {
                    if (positions[pos] == null) {
                        legalPositions.add(pos)
                    }
                    if (positions[pos] != null) {
                        if (positions[pos]?.getcolor() == this.color) {
                            break
                        } else {
                            legalPositions.add(pos)
                            break
                        }
                    }
                    if (posx == 7 || posx == 0) {
                        break
                    }
                }
            }
        }
        if (x != 7 && y != 7) {
            for (i in 1..7) {
                val pos = position + 9 * i
                val posx = pos % 8
                if (pos < 64) {
                    if (positions[pos] == null) {
                        legalPositions.add(pos)
                    }
                    if (positions[pos] != null) {
                        if (positions[pos]?.getcolor() == this.color) {
                            break
                        } else {
                            legalPositions.add(pos)
                            break
                        }
                    }
                    if (posx == 7 || posx == 0) {
                        break
                    }
                }
            }
        }
        if (x != 7 && y != 0) {
            for (i in 1..7) {
                val pos = position - 7 * i
                val posx = pos % 8
                if (pos > 0) {
                    if (positions[pos] == null) {
                        legalPositions.add(pos)
                    }
                    if (positions[pos] != null) {
                        if (positions[pos]?.getcolor() == this.color) {
                            break
                        } else {
                            legalPositions.add(pos)
                            break
                        }
                    }
                    if (posx == 7 || posx == 0) {
                        break
                    }
                }
            }
        }
    }
}
