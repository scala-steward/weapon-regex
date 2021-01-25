package weaponregex

import weaponregex.parser.Parser
import weaponregex.mutator.TreeMutator._
import weaponregex.model.mutation._
import weaponregex.mutator.BuiltinMutators

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation._
import scala.util.{Failure, Success}

/** The API facade of Weapon regeX for JavaScript
  * @note For JavaScript use only
  */
object WeaponRegeXJS {

  /** JavaScript Dictionary that maps from token mutator class names to the associating token mutators
    */
  @JSExportTopLevel("mutators")
  val allMutators: js.Map[String, TokenMutatorJS] =
    BuiltinMutators.all
      .map(mutator => mutator.getClass.getSimpleName.split("\\$$").head -> TokenMutatorJS(mutator))
      .toMap
      .toJSMap

  class MutationOptions(
      val mutators: js.Array[TokenMutatorJS] = null,
      val mutationLevels: js.Array[Int] = null
  ) extends js.Object

  /** Mutate using the given mutators at some specific mutation levels
    * @param pattern Input regex string
    * @param options JavaScript object for Mutation options
    * {{{
    * {
    *   mutators: [Mutators to be used for mutation],
    *   mutationLevels: [Target mutation levels. If this is `null`, the `mutators` will not be filtered],
    * }
    * }}}
    * @return A JavaScript Array of [[weaponregex.model.mutation.Mutant]] if can be parsed, or throw an exception otherwise
    */
  @JSExportTopLevel("mutate")
  def mutate(pattern: String, options: MutationOptions = new MutationOptions()): js.Array[MutantJS] = {
    val mutators: Seq[TokenMutator] =
      if (options.hasOwnProperty("mutators") && options.mutators != null)
        options.mutators.toSeq map (_.tokenMutator)
      else BuiltinMutators.all

    val mutationLevels: Seq[Int] =
      if (options.hasOwnProperty("mutationLevels") && options.mutationLevels != null)
        options.mutationLevels.toSeq
      else null

    Parser(pattern) match {
      case Success(tree)                 => (tree.mutate(mutators, mutationLevels) map MutantJS).toJSArray
      case Failure(throwable: Throwable) => throw throwable
    }
  }
}
