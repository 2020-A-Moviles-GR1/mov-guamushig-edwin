/**
 * Usuario.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {
  attributes: {
    cedula: {
      type: 'string',
      required: true,
      allowNull: false,
      columnName: 'usuario_cedula',
      unique: true,
      minLength: 10,
      maxLength: 25,
    },
    nombre: {
      type: 'string',
      minLength: 3,
      maxLength: 60,
      required: true,
      unique: true
    },
    correo: {
      type: 'string',
      isEmail: true
    },
    estadoCivil: {
      type: 'string',
      isIn: ['soltero', 'casado', 'divorciado', 'viudo', 'union libre'],
      defaultsTo: 'soltero'
    },
    password: {
      type: 'string',
      regex: /^[A-z0-9 ]*$/i
    },
    pokemons: {
      collection: 'pokemon',
      via: 'usuario'
    }
  },
  tableName: 'usuario',
};

