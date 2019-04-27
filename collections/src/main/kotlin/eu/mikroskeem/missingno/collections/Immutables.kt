/*
 * This file is part of project Missingno, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2019 Mark Vainomaa <mikroskeem@mikroskeem.eu>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package eu.mikroskeem.missingno.collections

import java.util.Collections

/**
 * Immutable collection utilities
 *
 * @author Mark Vainomaa
 */

/**
 * Wraps [Set] with [Collections.UnmodifiableSet] to make it unmodifiable
 */
fun <T> Set<T>.toUnmodifiable(copy: Boolean = false): Set<T> {
    return Collections.unmodifiableSet(if(copy) HashSet(this) else this)
}

/**
 * Wraps [List] with [Collections.UnmodifiableList] to make it unmodifiable
 *
 * @param copy Whether list should be copied first or not
 */
fun <T> List<T>.toUnmodifiable(copy: Boolean = false): List<T> {
    return Collections.unmodifiableList(if(copy) ArrayList(this) else this)
}

/**
 * Wraps [Collection] with [Collections.UnmodifiableCollection] to make it unmodifiable
 */
fun <T> Collection<T>.toUnmodifiable(): Collection<T> = Collections.unmodifiableCollection(this)