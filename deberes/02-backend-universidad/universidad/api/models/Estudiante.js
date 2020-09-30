/**
 * Estudiante.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    nombre: {
      columnName: 'nombre',
      type: 'string',
      minLength: 3,
      maxLength: 60,
      required: true,
    },
    fechaNacimiento: {
      columnName: 'fechaNacimiento',
      type: 'string',
      required: true,
    },
    sexo: {
      columnName: 'sexo',
      type: 'string',
      required: true,
      isIn: ['M', 'F'],
    },
    estatura: {
      columnName: 'estatura',
      type: 'number',
      required: false,
      allowNull: true,
    },
    estado: {
      columnName: 'estado',
      type: 'number',
      isIn: [0, 1],
      required: false,
      defaultsTo: 1
    },
    tieneBeca: {
      columnName: 'tieneBeca',
      type: 'number',
      isIn: [0, 1],
      required: false,
      defaultsTo: 1
    },
    latitud: {
      columnName: 'latitud',
      type: 'string',
      required: true
    },
    longitud: {
      columnName: 'longitud',
      type: 'string',
      required: true
    },
    urlImagen: {
      columnName: 'urlImagen',
      type: 'string',
      required: true
    },
    urlRedSocial: {
      columnName: 'urlRedSocial',
      type: 'string',
      required: true
    },
    universidad: {
      model: 'universidad',
      required: true
    },
  },
  tableName: 'estudiante',

};

