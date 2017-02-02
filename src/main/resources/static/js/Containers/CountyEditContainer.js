  var CountyEditContainer = React.createClass({
	  getInitialState: function() {
		    return {
		      countyDetails : [],
		      name : '',
		    };
		  },

		  componentWillMount: function() {
		    var self = this;
		    axios
		    .get('/county/' + this.props.params.id)
		    .then(function(response){
		      self.setState({
		        id : response.data.id,
		        name: response.data.name,
		      });
		    })
		    .catch(function(err){
		      console.error('CountyEditContainer.componentWillMount.axios.get/admin/county/:id', err);
		    });
		  },

		  onHandleChangeName : function(event){
		    this.setState({name: event.target.value});
		  },

		  onHandleUpdate: function(event) {
		    var self = this;
		    axios
		    .put('/admin/county/' + self.state.id,
		      {
		        id : self.state.id,
		        name: self.state.name,})
		    .then(function(response){
		      console.log(response.data);
		      self.context.router.push('/admin/county');
		    })
		    .catch(function(err){
		      console.error('Update failed at CountyEditContainer - ', err);
		    });
		    event.preventDefault();
		  },
		  
		  onHandleCancel:function(){
		    this.context.router.push('/admin/county');
		  },

		  render: function() {
		    return (
		      <CountyEditFormComponent
		      submitButtonName='Išsaugoti'
		      
		      onHandleChangeName={this.onHandleChangeName}

		      onHandleUpdate={this.onHandleUpdate}
		      onHandleCancel={this.onHandleCancel}

		      name={this.state.name}
		      />

		    );
		  }
		});

	CountyEditContainer.contextTypes = {
	  router: React.PropTypes.object.isRequired,
	};
	
	window.CountyEditContainer = CountyEditContainer ;
